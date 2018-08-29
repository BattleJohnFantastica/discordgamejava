import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.mongodb.morphia.query.Query;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

public class DisplayAnswers {
    public void display(Group group, MessageReceivedEvent event){
        try{

            Query<Answer> query = group.data.createQuery(Answer.class);
            Query<Answer> filter = query.filter("gameGroupId ==", group.groupId);
            Long length = filter.count();

            System.out.println(String.format("FILTER LENGTH %s", String.valueOf(length)));
            if(length == 0){
                System.out.println("length is 0");
                group.state = "ready";
                group.data.save(group);
                throw new IllegalArgumentException("No answers given, ending game");
            }
            int[] a = new int[length.intValue()];
            for (int i = 0; i < length.intValue(); ++i) {
                a[i] = i + 1;
            }
            int[] b = RandomizeArray(a);
            List<Answer> list = filter.asList();
            ArrayList<String> reply = new ArrayList<>();
            reply.add( "\"" + group.question + "\"\n\n");
            for (int i = 0; i < list.size(); ++i) {
                int random = b[i] - 1;
                Answer answer = list.get(random);
                answer.displayNumber = String.valueOf(i + 1);
                group.data.save(answer);
                String iterationReply = String.format("\nA%s: %s", answer.displayNumber, answer.answerText);
                reply.add(iterationReply);
            }
            reply.add("\n\nYou have 30 seconds to vote for your favourite!\n\n***********************************");
            String stringReply = String.join("", reply);
            group.fun.nextState(group);
            System.out.print(stringReply);
            event.getChannel().sendMessage(stringReply).queue();
            ScheduledExecutorService scheduler = newSingleThreadScheduledExecutor();
            Runnable task = () -> new CountVotes().display(group, event);
            scheduler.schedule(task, 30, TimeUnit.SECONDS);

        } catch(IllegalArgumentException e) {
            System.out.println("reached the illegal argument");
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
    public static int[] RandomizeArray(int[] array){
        Random rgen = new Random();  // Random number generator

        for (int i=0; i<array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }
}

