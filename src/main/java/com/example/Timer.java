package com.example;
import java.util.concurrent.*;

public class Timer {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Timer(int delayInSeconds, Runnable task) {
        scheduler.schedule(() -> {
            task.run();
            scheduler.shutdown(); // leállítjuk, ha nem akarunk több feladatot
        }, delayInSeconds, TimeUnit.SECONDS);
    }

    public void cancel() {
        scheduler.shutdownNow();
    }
}
