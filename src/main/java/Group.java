import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

@Entity("groups")
public class Group {
    @Id
    public String groupId;
    public String name;
    public String question;
    public String state;
    public String code;
    @Transient
    public Datastore data;
    @Transient
    public Functions fun = new Functions();
}

class Functions{
    public void updateQuestions(Group group){
        group.question = new Questions().random();
        group.data.save(group);
    }
    public Boolean wrongState(Group group, String msg){
        String state = group.state;
        if (msg.startsWith("/ping")) {
            return false;
        }
        if (msg.startsWith("/help")) {
            return false;
        }
        if (msg.startsWith("/question")) {
            return !state.equals("ready") && !state.equals("answers");
        }
        if (msg.startsWith("/shuffle")) {
            return !state.equals("ready");
        }
        if (msg.startsWith("/begin")) {
            return !state.equals("ready");
        }
        return false;
    }
    public void nextState(Group group){
        String state = group.state;
        if(state.equals("ready")){
            group.state = "answers";
            group.data.save(group);
        }
        if(state.equals("answers")){
            group.state = "voting";
            group.data.save(group);
        }
        if(state.equals("voting")){
            group.state = "ready";
            group.data.save(group);
        }
    }
}