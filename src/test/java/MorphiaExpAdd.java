import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MorphiaExpAdd {
    public static void main(String args[]){
        final Morphia morphia = new Morphia();
        morphia.mapPackage("org.mongodb.morphia.example");
        final Datastore datastore = morphia.createDatastore(new RemoteMongoClient().mongoClient(), "morphia_example");
        datastore.ensureIndexes();
        final Group elmer = new Group();
        elmer.groupId = "here is is";
        elmer.name = "and the name";
        datastore.save(elmer);
    }
}
