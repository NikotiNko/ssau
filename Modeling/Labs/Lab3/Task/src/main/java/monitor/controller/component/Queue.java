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
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public void tick() {
        Iterator<Transact> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Transact t = iterator.next();
            Integer time = t.getParams().get("queueTime");
            if (time > queueMaxTime) {
                iterator.remove();
                // TODO: 21.11.2016
            }else {
                time++;
                t.getParams().put("queueTime", time);
            }
        }
        if (!device.isBusy() && queue.size() != 0) {
            device.advance(queue.peek());
        }
    }

    public void add(Transact transact) {
        if (queue.size()<queueLen) {
            transact.incBlockId();
            transact.getParams().put("queueTime", 0);
            queue.offer(transact);

        }else {
            transact.setBlockId(3);
            // TODO: 21.11.2016
        }
    }
}
