package Controller;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import javax.print.Doc;
import java.net.UnknownHostException;

public class ChartController {


    void insertDatainCollection(String SymbolName, MongoDatabase db) {

        MongoCollection<Document> dbCollection = db.getCollection("stockTest");


// Query and take the latetst symbol
//        BasicDBObject whereQuery=new BasicDBObject();
//        whereQuery.put("Symbol","ACC");
//
//        DBCursor dbCursor=dbCollection.find(whereQuery);
//        System.out.println(dbCursor.next().get(""));

//        Document document = dbCollection.find(Filters.eq("Symbol", SymbolName)).first();

        MongoCursor<Document> mongoCursor = dbCollection.find(Filters.eq("Symbol", SymbolName))
                .sort(Sorts.descending("timestamp"))
                .limit(3)
                .iterator();

        Document newDoc = new Document();

        int count = 0;
        while (mongoCursor.hasNext()) {
//            System.out.println(mongoCursor.next());
            Document internalDoc = mongoCursor.next();
            if (count == 0) {
                newDoc.append("Symbol", internalDoc.getString("Symbol"));
                newDoc.append("Price CHG %", Double.valueOf(internalDoc.get("Chg %","0.0")));
                newDoc.append("OI CHG %", Double.valueOf(internalDoc.get("Increase %","0.0")));
                newDoc.append("VOL CHG %", Double.valueOf(internalDoc.get("% Change","0.0")));
            } else {

                newDoc.append("Prev " + count + " Price CHG %",Double.valueOf(internalDoc.get("Chg %","0.0")));
                newDoc.append("Prev " + count + " OI CHG %", Double.valueOf(internalDoc.get("Increase %","0.0")));
                newDoc.append("Prev " + count + " VOL CHG %", Double.valueOf(internalDoc.get("% Change","0.0")));
            }
            count++;
        }

        MongoCollection<Document> chartCollection = db.getCollection("chartCollection");
        chartCollection.insertOne(newDoc);

    }

    void clearCollection(MongoDatabase db){
        MongoCollection<Document> dbCollection = db.getCollection("chartCollection");
        dbCollection.drop();


    }

}
