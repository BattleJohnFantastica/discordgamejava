import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.mongodb.morphia.UpdateOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import static com.mongodb.client.model.Updates.inc;

public class PrivateMessageHandler {
    PrivateMessageHandler(MessageReceivedEvent event){
        if(!event.getAuthor().isBot()){
            String id = event.getAuthor().getId();
            String name = event.getAuthor().getName();
            String msg = event.getMessage().getContentDisplay();
            System.out.println(id.concat(name).concat(msg));
            Person person = new GetPerson().get(event);
            //event.getPrivateChannel().sendMessage("This is a test of the emergency broadcast system").queue();
            if(msg.startsWith("/code")) {
                String sub = msg.substring(5).trim();
                int index = sub.indexOf(' ');
                System.out.println(index);
                if(index == -1){
                    person.code = sub;
                    person.data.save(person);
                } else {
                    person.code = sub.substring(0, index);
                    person.data.save(person);
                }
                Query<Group> query = person.data.createQuery(Group.class);
                Query<Group> filter = query.filter("code ==", person.code);
                long quantity = filter.count();
                if(quantity == 0) {
                    event.getPrivateChannel().sendMessage("I'm afraid the group you're looking for does not exist").queue();
                } else if (quantity == 1) {
                    Group groupMatchesCode = filter.get();
                    person.gameGroupId = groupMatchesCode.groupId;
                    person.gameGroupName = groupMatchesCode.name;
                    person.data.save(person);
                    event.getPrivateChannel().sendMessage("Well now, looks like you've been added to the game").queue();

                } else {
                    event.getPrivateChannel().sendMessage("There's been an error, two groups with the same code, please contact the administrator").queue();

                }

            }
            if(msg.startsWith("/group")) {
                String reply = String.format("You are currently in the group: %s", person.gameGroupName);
                event.getPrivateChannel().sendMessage(reply).queue();
            }
            if(msg.startsWith("/vote")){
                try{
                    Group gameGroup = person.fun.getGameGroup("voting");
                    VotingDetails votingDetails = new GetDisplayNumber().get(msg, person, event);
                    String displayNumber = votingDetails.displayNumber;
                    Query<Answer> query = person.data.createQuery(Answer.class).filter("displayNumber ==", displayNumber);
                    if(person.hasVoted) throw new IllegalArgumentException("I'm afraid you've already voted");
                    if(votingDetails.idOfAuthorOfAnswer.equals(person.id)) throw new IllegalArgumentException("Voting for yourself hey? NOT ALLOWED");
                    person.hasVoted = true;
                    person.data.save(person);
                    UpdateOperations<Answer> updater = person.data.createUpdateOperations(Answer.class).inc("votes", 1);
                    person.data.update(query, updater);
                    event.getPrivateChannel().sendMessage(String.format("You voted for \"%s\"", votingDetails.answer)).queue();
                } catch( IllegalArgumentException ex){
                    event.getPrivateChannel().sendMessage(ex.getMessage()).queue();
                } catch(NullPointerException nu) {
                    System.out.println(nu.getCause());
                }
            }
            if(msg.startsWith("/answer")){
                try
                {
                    Group gameGroup = person.fun.getGameGroup("answers");
                    String sub = msg.substring(7).trim();
                    int subLength = sub.length();
                    if(subLength == 0){
                        event.getPrivateChannel().sendMessage("You did not submit an answer I CAN TELL").queue();
                    } else if (subLength > 200 ){
                        event.getPrivateChannel().sendMessage("The answer you submitted was much too long").queue();
                    } else {
                        Answer answer = new GetAnswer().get(event);
                        answer.gameGroupId = person.gameGroupId;
                        answer.answerText = sub;
                        answer.personsName = person.name;
                        answer.data.save(answer);
                        System.out.println(sub);
                        event.getPrivateChannel().sendMessage("Answer submitted").queue();
                    }
                } catch( IllegalArgumentException ex){
                    event.getPrivateChannel().sendMessage(ex.getMessage()).queue();
                }
            }
        }
    }
}

