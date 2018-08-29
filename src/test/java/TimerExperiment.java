import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

public class TimerExperiment {
    public static void main(String args[]){
        ScheduledExecutorService scheduler = newSingleThreadScheduledExecutor();
        Runnable task = () -> System.out.println("Hi!");
        scheduler.schedule(task, 10, TimeUnit.SECONDS);
        System.out.println("this should print first");
    }
}