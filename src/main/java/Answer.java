import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

@Entity("answer")
public class Answer {
    @Id
    public String id;
    // this will have to be the users ID
    public String gameGroupId;
    public String personsName;
    public String answerText;
    public int votes;
    public String displayNumber;
    @Transient
    Datastore data;
}
