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
    private Transact currentTransact;

    public Generator(MonitorView monitorView, RandomGenerator generateRandomGenerator, int count, Queue queue, Timer timer) {
        this.monitorView = monitorView;
        this.randomGenerator = generateRandomGenerator;
        this.count = count;
        this.currentCount = 0;
        currentTransact = new Transact(timer);
        this.lastTime = randomGenerator.generate();
        this.queue = queue;
        this.timer = timer;
        System.out.println("Generator CREATED");
    }

    public boolean hasNext() {
        return currentCount <= count;
    }

    public void tick() {
        lastTime--;
        System.out.println("Generator TICK, last time:" + lastTime);
        if (lastTime <= 0) {
            count++;
            currentTransact.setBlock("Queue");
            queue.add(currentTransact);
            if (currentCount <= count) {
                currentTransact = new Transact(timer);
                lastTime = randomGenerator.generate();
                System.out.println("Generator GENERATE NEW TRANSACT:" + currentTransact.getId());
            }else {
                System.out.println("Generator CAN NOT GENERATE NEW TRANSACT");
            }
        }
    }
}
