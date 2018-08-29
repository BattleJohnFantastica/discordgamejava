import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class GetCodeTest {

    public static void main(String args[]) {
        final Morphia morphia = new Morphia();
        morphia.mapPackage("org.mongodb.morphia.example");
        final Datastore datastore = morphia.createDatastore(new RemoteMongoClient().mongoClient(), "messages");
        datastore.ensureIndexes();
        String uuid = new GetCode().uniqueCode(datastore);
        System.out.println(uuid);
    }
}
