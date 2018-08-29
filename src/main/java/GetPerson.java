import com.mongodb.MongoClient;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

public class GetPerson {
    public Person get(MessageReceivedEvent event){
        String personId = event.getAuthor().getId();
        final Morphia morphia = new Morphia();
        morphia.mapPackage("org.mongodb.morphia.example");
        final Datastore datastore = morphia.createDatastore(new RemoteMongoClient().mongoClient(), "messages");
        datastore.ensureIndexes();
        Query<Person> query = datastore.createQuery(Person.class);
        long quantity = query.filter("_id ==", personId).count();
        System.out.println(quantity);
        if (quantity == 0){
            Person newPerson = new Person();
            newPerson.id = personId;
            newPerson.name = event.getAuthor().getName();
            newPerson.code = "NONE";
            newPerson.gameGroupId = "NONE";
            newPerson.gameGroupName = "NONE";
            newPerson.hasVoted = false;
            newPerson.data = datastore;
            datastore.save(newPerson);
            return newPerson;
        } else {
            Person existingPerson = datastore.get(Person.class, personId);
            existingPerson.data = datastore;
            return existingPerson;
        }
    }
}
