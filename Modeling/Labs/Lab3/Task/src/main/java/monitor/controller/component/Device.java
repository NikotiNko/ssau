package monitor.controller.component;

import monitor.controller.entity.Transact;
import monitor.service.generator.RandomGenerator;
import monitor.view.MonitorView;

/**
 * Created by Shpulka on 21.11.2016.
 */
public class Device {

    private MonitorView monitorView;
    private RandomGenerator randomGenerator;
    private int lastTime;
    private Transact currentTransact;

    public Device(MonitorView monitorView, RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
        this.monitorView = monitorView;
        this.lastTime = 0;
        System.out.println("Device CREATED");
    }

    public boolean isBusy(){
        return lastTime > 0;
    }

    
    public void tick() {
        lastTime--;
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
        lastTime = randomGenerator.generate();
    }
}
