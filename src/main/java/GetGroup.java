import com.mongodb.MongoClient;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GetGroup {
    public Group get(MessageReceivedEvent event){
        MessageChannel channel = event.getChannel();
        String groupId = channel.getId();
        String serverName = event.getGuild().getName();
        final Morphia morphia = new Morphia();
        morphia.mapPackage("org.mongodb.morphia.example");
        final Datastore datastore = morphia.createDatastore(new RemoteMongoClient().mongoClient(), "messages");
        datastore.ensureIndexes();
        Query<Group> query = datastore.createQuery(Group.class);
        long quantity = query.filter("_id ==", groupId).count();
        if(quantity == 0){
            Group newGroup = new Group();
            newGroup.groupId = groupId;
            newGroup.name = serverName;
            System.out.print(new Questions().random());
            newGroup.question = new Questions().random();
            newGroup.state = "ready";
            newGroup.data = datastore;
            newGroup.code = new GetCode().uniqueCode(datastore);
            datastore.save(newGroup);
            return newGroup;
        } else {
            Group existingGroup = datastore.get(Group.class, groupId);
            existingGroup.data = datastore;
            return existingGroup;
        }
    }
}
