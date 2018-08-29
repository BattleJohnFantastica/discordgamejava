import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.ArrayList;
import java.util.Collections;

public class CountVotes {
    public void display(Group group, MessageReceivedEvent event){
        ArrayList<String> reply = new ArrayList<>();
        reply.add("\n ***");
        Query<Answer> query = group.data.createQuery(Answer.class).order("votes");
        query.forEach((an) -> {
            ArrayList<String> subReply = new ArrayList<>();
            subReply.add(an.answerText);
            subReply.add("  : Has ");
            subReply.add(String.valueOf(an.votes));
            subReply.add(" votes \nSaid by ");
            subReply.add(an.personsName);
            subReply.add("\n\n");
            reply.add(String.join("", subReply));
        });
        reply.add("\"\n\n");
        reply.add(group.question);
        reply.add("For the question \"");
        Collections.reverse(reply);
        String stringReply = String.join("", reply);
        group.fun.nextState(group);
        Query<Person> queryTwo = group.data.createQuery(Person.class).filter("gameGroupId ==", group.groupId);
        UpdateOperations<Person> updater = group.data.createUpdateOperations(Person.class).set("hasVoted", false);
        group.data.update(queryTwo, updater);
        Query<Answer> queryThree = group.data.createQuery(Answer.class).filter("gameGroupId ==", group.groupId);
        group.data.delete(queryThree);
        event.getChannel().sendMessage(stringReply).queue();
    }
}
