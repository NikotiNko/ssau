package monitor.controller.component;

import monitor.controller.entity.Transact;
import monitor.service.generator.RandomGenerator;
import monitor.view.MonitorView;

/**
 * Created by Shpulka on 21.11.2016.
 */
public class Generator {

    private MonitorView monitorView;
    private RandomGenerator randomGenerator;
    private int count;
    private int currentCount;
    private int lastTime;
    private Queue queue;
    private Timer timer;

    public Generator(MonitorView monitorView, RandomGenerator generateRandomGenerator, int count, Queue queue, Timer timer) {
        this.monitorView = monitorView;
        this.randomGenerator = generateRandomGenerator;
        this.count = count;
        this.currentCount = 0;
        this.lastTime = randomGenerator.generate();
        this.queue = queue;
        this.timer = timer;
    }

    public boolean hasNext() {
        return currentCount < count;
    }

    public void tick() {
        if (lastTime <= 0) {
            Transact transact = new Transact(timer);
            queue.add(transact);
            lastTime = randomGenerator.generate();
        }
        lastTime--;
    }
}
