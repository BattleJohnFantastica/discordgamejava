import com.mongodb.MongoClient;
import org.bson.Document;

public class RemoteMongoClientTest {
    public static void main(String[] args) {
        MongoClient RMC = new RemoteMongoClient().mongoClient();
        Iterable<Document> doc = RMC.getDatabase("messages").getCollection("website_messages").find();
        doc.forEach((k) -> System.out.println(k.toJson()));
    }
}
