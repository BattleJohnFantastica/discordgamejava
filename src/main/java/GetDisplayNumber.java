import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.mongodb.morphia.query.Query;

public class GetDisplayNumber {
    public VotingDetails get(String msg, Person person, MessageReceivedEvent event){
        String displayNumber = msg.substring(5).trim();
        try {
            Query query = person.data.createQuery(Answer.class).filter("displayNumber ==", displayNumber);
            Long length = query.count();
            if (length == 0 ) throw new IllegalArgumentException("There is no answer by that number");
            if (length > 1 ) throw new IllegalArgumentException("There has been an error, please contact administrator");
            Answer answer = (Answer) query.get();
            System.out.println("NO ERRRORS FROM GETDISPLAY NUMBER");
            VotingDetails votingDetails = new VotingDetails();
            votingDetails.answer = answer.answerText;
            votingDetails.displayNumber = displayNumber;
            votingDetails.idOfAuthorOfAnswer = answer.id;
            return votingDetails;

        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("I don't think you submitted a number there bub");
        }


    }
}
