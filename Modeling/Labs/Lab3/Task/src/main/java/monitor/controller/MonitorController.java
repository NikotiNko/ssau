package monitor.controller;


import monitor.controller.component.Device;
import monitor.controller.component.Generator;
import monitor.controller.component.Queue;
import monitor.controller.component.Timer;
import monitor.service.generator.RandomGenerator;
import monitor.view.MonitorView;

public class MonitorController {

    private Device device;
    private Generator generator;
    private Queue queue;
    private Timer timer;
    private boolean isInterrupted;

    public MonitorController(
            MonitorView monitorView,
            RandomGenerator generateRandomGenerator,
            RandomGenerator advanceRandomGenerator,
            int queueLen,
            int queueMaxTime,
            int transactCount
    ) {
        this.device = new Device(monitorView, advanceRandomGenerator);
        this.queue = new Queue(monitorView, queueLen, queueMaxTime,device);
        this.timer = new Timer(monitorView);
        this.generator = new Generator(monitorView, generateRandomGenerator, transactCount, queue, timer);
    }

    public void start() throws InterruptedException {
        while ((generator.hasNext() || !queue.isEmpty() || device.isBusy()) && (!isInterrupted)) {
            generator.tick();
            queue.tick();
            device.tick();
            timer.tick();
        }
    }

    public void stop() {
        isInterrupted = true;
    }
}
