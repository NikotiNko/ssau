package monitor.controller.component;

import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import monitor.controller.entity.Transact;
import monitor.service.generator.RandomGenerator;
import monitor.view.MonitorView;

/**
 * Created by Shpulka on 21.11.2016.
 */
public class Generator {

    private RandomGenerator randomGenerator;
    private int count;
    private int currentCount;
    private int lastTime;
    private int lastGeneratedTime;
    private Queue queue;
    private Timer timer;
    private Transact currentTransact;
    private ProgressIndicator loader;

    public Generator(RandomGenerator generateRandomGenerator, int count, Queue queue, Timer timer,ProgressIndicator loader) {
        this.randomGenerator = generateRandomGenerator;
        this.count = count;
        this.currentCount = 0;
        currentTransact = new Transact(timer);
        this.lastGeneratedTime = randomGenerator.generate();
        this.lastTime = randomGenerator.generate();
        this.queue = queue;
        this.timer = timer;
        this.loader = loader;
        System.out.println("Generator CREATED");
    }

    public boolean hasNext() {
        return currentCount <= count;
    }

    public void tick() {
        lastTime--;
        System.out.println("Generator TICK, last time:" + lastTime);
        if (lastTime >= 0) {
            Platform.runLater(() -> loader.setProgress(1 - (double)lastTime / lastGeneratedTime));
        }
        if (lastTime == 0) {
                currentCount++;
                currentTransact.setBlock("Queue");
                queue.add(currentTransact);
            if (currentCount <= count) {
                currentTransact = new Transact(timer);
                lastGeneratedTime = randomGenerator.generate();
                lastTime = lastGeneratedTime;
                System.out.println("Generator GENERATE NEW TRANSACT:" + currentTransact.getId());
            }else {
                currentTransact = null;
                System.out.println("Generator CAN NOT GENERATE NEW TRANSACT, Count:" + currentCount);
            }
        }
    }
}
