package NSEData;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.bson.Document;
import org.json.JSONArray;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONReader {
    public void execute() throws InterruptedException {



            Response response = RestAssured.get("https://www.nseindia.com/live_market/dynaContent/live_watch/stock_watch/niftyStockWatch.json");
//        System.out.println(response.prettyPrint());
            List<Object> listJson = response.jsonPath().getList("data");
            JSONArray jsonArray = new JSONArray(listJson);
            System.out.println(jsonArray);

            MongoClient mongoClient = new MongoClient("localhost");
            MongoDatabase db = mongoClient.getDatabase("test");
//            DB db = mongoClient.getDB("test");
            MongoCollection dbCollection = db.getCollection("nseCollection");

// Convert List to JsonArray
            List<Document> documentList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Document jsonObject = Document.parse(jsonArray.get(i).toString());
//            jsonObject.put("trdVol",Double.valueOf(jsonObject.getString("trdVol")));
//            jsonObject.put("iislPtsChange",Double.valueOf(jsonObject.getString("iislPtsChange")));
            jsonObject.put("timestamp",System.currentTimeMillis());
                documentList.add(jsonObject);
            }

            dbCollection.insertMany(documentList);

// Creating chart collection after this
            new NseChartController().clearCollection(db);
            for (int i = 0; i < jsonArray.length(); i++) {
                String symbolName = (String) Document.parse(jsonArray.get(i).toString()).get("symbol");
                System.out.println("Symbol Name : " + symbolName);
                new NseChartController().insertDatainCollection(symbolName, db);
            }


            mongoClient.close();

        }
    }

