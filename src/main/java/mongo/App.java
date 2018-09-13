package mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.*;


public class App {
    public static void main(String[] args) throws IOException {
        MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase db = client.getDatabase("students");
        MongoCollection<Document> grades = db.getCollection("grades");

        ArrayList<Document> into = grades.find().into(new ArrayList<>());

        Map<Integer,Double> scoreMap = new HashMap<>();
        Map<Integer,ObjectId> lowscores = new HashMap<>();

        for (Document doc: into){
            Double score = scoreMap.get(doc.get("student_id"));

            if ((score == null) || ((Double) doc.get("score") < score)){
                scoreMap.put((Integer)doc.get("student_id"),score);
                lowscores.put((Integer)doc.get("student_id"),(ObjectId) doc.get("_id"));
            }
        }
        System.out.println(scoreMap.size());
        System.out.println(lowscores.size());

//        for (ObjectId id:lowscores.values()) {
//            grades.deleteOne(new Document("_id",id));
//        }


    }

}
