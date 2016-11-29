package monitor.controller.component;

import monitor.controller.entity.Transact;
import monitor.view.MonitorView;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Shpulka on 21.11.2016.
 */
public class Queue {

    private MonitorView monitorView;
    private java.util.Queue<Transact> queue = new LinkedList<Transact>();
    private int queueLen;
    private int queueMaxTime;
    private Device device;

    public Queue(MonitorView monitorView, int queueLen, int queueMaxTime, Device device) {
        this.monitorView = monitorView;
        this.queueLen = queueLen;
        this.queueMaxTime = queueMaxTime;
        this.device = device;
        System.out.println("Queue CREATED");
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void tick() {
        System.out.println("Queue TICK, queue size:" + queue.size());
        Iterator<Transact> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Transact t = iterator.next();
            Integer time = t.getParams().get("queueTime");
            if (time > queueMaxTime) {
                iterator.remove();
                // TODO: 21.11.2016 STATISTIC
                System.out.println("Transact" + t.getId() + " DELETED FROM QUEUE, out of time!");
            } else {
                time++;
                t.getParams().put("queueTime", time);
            }
        }
        if (!device.isBusy() && queue.size() != 0) {
            Transact peeked = queue.peek();
            System.out.println("Transact" + peeked.getId() + " GO TO DEVICE");
            device.advance(peeked);
        }
    }

    public void add(Transact transact) {
        System.out.println("Transact" + transact.getId() + " OFFER TO QUEUE");
        if (queue.size() < queueLen) {
            transact.getParams().put("queueTime", 0);
            queue.offer(transact);
            System.out.println("Transact" + transact.getId() + " ADDED TO QUEUE");
        } else {
            transact.setBlock("Deleted");
            // TODO: 21.11.2016 STATISTIC
            System.out.println("Transact DELETED FROM QUEUE, queue overload!");
        }
    }
}
