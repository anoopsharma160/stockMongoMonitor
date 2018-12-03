package NSEData;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

public class NseChartController {


public     void insertDatainCollection(String SymbolName, MongoDatabase db) {

        MongoCollection<Document> dbCollection = db.getCollection("nseCollection");


// Query and take the latetst symbol
//        BasicDBObject whereQuery=new BasicDBObject();
//        whereQuery.put("Symbol","ACC");
//
//        DBCursor dbCursor=dbCollection.find(whereQuery);
//        System.out.println(dbCursor.next().get(""));

//        Document document = dbCollection.find(Filters.eq("Symbol", SymbolName)).first();

        MongoCursor<Document> mongoCursor = dbCollection.find(Filters.eq("symbol", SymbolName))
                .sort(Sorts.descending("timestamp"))
                .limit(4)
                .iterator();

        Document newDoc = new Document();

        int count = 0;
        while (mongoCursor.hasNext()) {
//            System.out.println(mongoCursor.next());
            Document internalDoc = mongoCursor.next();
            if (count == 0) {
                newDoc.append("Symbol", internalDoc.getString("symbol"));
                newDoc.append("Price CHG %", Double.valueOf(internalDoc.get("per","0.0")));
                newDoc.append("Traded Volume", Double.valueOf(internalDoc.get("trdVol","0.0")));
            } else {

                newDoc.append("Prev " + count + " Price CHG %",Double.valueOf(internalDoc.get("per","0.0")));
                newDoc.append("Prev " + count + " Traded Vol ", Double.valueOf(internalDoc.get("trdVol","0.0")));
            }
            count++;
        }

        MongoCollection<Document> chartCollection = db.getCollection("NSEChartCollection");
        chartCollection.insertOne(newDoc);

    }

    void clearCollection(MongoDatabase db){
        MongoCollection<Document> dbCollection = db.getCollection("NSEChartCollection");
        dbCollection.drop();


    }

}
