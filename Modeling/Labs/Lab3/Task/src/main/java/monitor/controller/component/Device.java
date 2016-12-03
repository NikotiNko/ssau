package monitor.controller.component;

import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import monitor.controller.entity.Transact;
import monitor.service.generator.RandomGenerator;
import monitor.view.MonitorView;

/**
 * Created by Shpulka on 21.11.2016.
 */
public class Device {

    private RandomGenerator randomGenerator;
    private int lastTime;
    private int lastGeneratedTime;
    private Transact currentTransact;
    private ProgressIndicator loader;

    public Device(RandomGenerator randomGenerator, ProgressIndicator loader) {
        this.randomGenerator = randomGenerator;
        this.lastGeneratedTime = 0;
        this.lastTime = lastGeneratedTime;
        this.loader = loader;
        System.out.println("Device CREATED");
    }

    public boolean isBusy() {
        return lastTime > 0;
    }


    public void tick() {
        lastTime--;
        if (lastTime >= 0) {
            Platform.runLater(() -> loader.setProgress(1 - (double)lastTime / lastGeneratedTime));
        }
        System.out.println("Device TICK, last time:" + lastTime);
        if (lastTime == 0) {
            System.out.println("Device FREE, Transact " + currentTransact.getId() + " FREE");
            currentTransact.setBlock("Free");
            currentTransact = null;
        }
    }

    public void advance(Transact transact) {
        System.out.println("Device ADVANCED");
        currentTransact = transact;
        lastGeneratedTime = randomGenerator.generate();
        lastTime = lastGeneratedTime;
    }
}
