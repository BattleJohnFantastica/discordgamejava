import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

public class MorphiaExperiment {
    public static void main(String args[]){
        final Morphia morphia = new Morphia();
        morphia.mapPackage("org.mongodb.morphia.example");
        final Datastore datastore = morphia.createDatastore(new RemoteMongoClient().mongoClient(), "morphia_example");
        datastore.ensureIndexes();
        Query<Group> query = datastore.createQuery(Group.class);
        Query<Group> here_is_is = query.filter("_id ==", "here  is");
        System.out.print(here_is_is.count());
    }
}
