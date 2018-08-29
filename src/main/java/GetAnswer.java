import com.mongodb.MongoClient;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

public class GetAnswer {
    public Answer get(MessageReceivedEvent event){
        String personId = event.getAuthor().getId();
        final Morphia morphia = new Morphia();
        morphia.mapPackage("org.mongodb.morphia.example");
        final Datastore datastore = morphia.createDatastore(new RemoteMongoClient().mongoClient(), "messages");
        datastore.ensureIndexes();
        Query<Answer> query = datastore.createQuery(Answer.class);
        Query givenAnswer = query.filter("_id ==", personId);
        Long length = givenAnswer.count();
        if(length == 0){
            Answer newAnswer = new Answer();
            newAnswer.id = personId;
            newAnswer.gameGroupId =  "NONE";
            newAnswer.answerText = "NONE";
            newAnswer.personsName = "NONE";
            newAnswer.votes = 0;
            newAnswer.data = datastore;
            newAnswer.data.save(newAnswer);
            return newAnswer;
        } else {
            Answer existingAnswer = datastore.get(Answer.class, personId);
            existingAnswer.data = datastore;
            return existingAnswer;
        }

    }
}
