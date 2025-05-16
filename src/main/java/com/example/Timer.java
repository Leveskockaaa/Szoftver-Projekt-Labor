package com.example;
import java.util.concurrent.*;

public class Timer {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Timer(int delayInSeconds, Runnable task) {
        System.out.println("Timer created");
        scheduler.schedule(() -> {
            System.out.println("Timer fired");
            task.run();
            scheduler.shutdown(); // leállítjuk, ha nem akarunk több feladatot
        }, delayInSeconds, TimeUnit.SECONDS);
    }
}
