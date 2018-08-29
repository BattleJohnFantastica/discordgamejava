import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;
import org.mongodb.morphia.query.Query;

@Entity("people")
public class Person {
    @Id
    public String id;
    public String name;
    public String gameGroupId;
    public String code;
    public String gameGroupName;
    public Boolean hasVoted;
    @Transient
    public Datastore data;
    @Transient
    public PersonFunctions fun = new PersonFunctions(this);
}

class PersonFunctions{
    public Person person;

    public PersonFunctions(Person person) {
        this.person = person;
    }

    public Group getGameGroup(String state){
        String gameGroupId = this.person.gameGroupId;
        Query<Group> query = person.data.createQuery(Group.class);
        //person.data.createUpdateOperations(Group.class).inc("vote", 1);
        Query<Group> filter = query.filter("_id ==", gameGroupId);
        System.out.printf("long %s", filter.count());
        long quantity = filter.count();
        if (quantity != 1) throw new IllegalArgumentException("You are not connected to an active game");
        Group group = filter.get();
        if (!group.state.equals(state)) throw new IllegalArgumentException("The game is not accepting that type of input at this time");
        else  {
            return group;
        }
    }
}