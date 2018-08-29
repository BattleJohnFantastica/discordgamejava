import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

public class PublicMessageHandler {
    PublicMessageHandler(MessageReceivedEvent event){
        MessageChannel channel = event.getChannel();
        String msg = event.getMessage().getContentDisplay();
        Group group = new GetGroup().get(event);
        if(msg.startsWith("/") && group.fun.wrongState(group, msg)){
            channel.sendMessage("You cannot perform that action at this time").queue();
        }
        else {

            if (msg.startsWith("/ping")) {
                channel.sendMessage("pong!").queue();
            }
            if (msg.startsWith("/help")) {
                channel.sendMessage("To begin /shuffle selects a question. If you don't want that one, /shuffle again. /begin starts the game with the selected question. You are given a code (CODE). Send to the bot /code CODE. Then send /answer ANSWER. Unless you change group, you do not need to reenter the code. To vote for your favourite you can send the bot /vote and then the number of the answer you liked best. If you haven't answered you can still vote, but you will still need to enter the code.").queue();
            }
            if (msg.startsWith("/question")) {
                String reply = String.format("The Chosen Question Is: \n \n%s \n \nThe code is %s \n \n",  group.question, group.code);
                channel.sendMessage(reply).queue();
            }
            if (msg.startsWith("/shuffle")) {
                group.fun.updateQuestions(group);
                String reply = String.format("The Current Question Is: \"%s\"", group.question);
                channel.sendMessage(reply).queue();
            }
            if (msg.startsWith("/begin")){
                group.fun.nextState(group);
                String reply = String.format("The Chosen Question Is: \n \n%s\n \nThe code is %s\n \nYou have 1 minute to enter your answer!\n\n ***********************************",  group.question, group.code);
                ScheduledExecutorService scheduler = newSingleThreadScheduledExecutor();
                Runnable task = () -> new DisplayAnswers().display(group, event);
                scheduler.schedule(task, 60, TimeUnit.SECONDS);
                channel.sendMessage(reply).queue();
            }
        }
    }
}
