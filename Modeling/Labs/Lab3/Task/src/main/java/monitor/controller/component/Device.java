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

    public Device(MonitorView monitorView, RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
        this.monitorView = monitorView;
        this.lastTime = 0;
    }

    public boolean isBusy(){
        return lastTime <= 0;
    }

    
    public void tick() {
        lastTime--;
    }

    public void advance(Transact transact) {
        transact.incBlockId();
        lastTime = randomGenerator.generate();
    }
}
