package monitor.controller.component;

import javafx.application.Platform;
import monitor.controller.entity.Transact;
import monitor.view.MonitorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shpulka on 21.11.2016.
 */
public class Timer {
    private static final int FIXED_RATE = 500;
    private MonitorView monitorView;
    private int time;
    private int lastId;
    private List<Transact> transacts;

    public Timer(MonitorView monitorView) {
        this.monitorView = monitorView;
        this.time = 0;
        this.lastId = 0;
        this.transacts = new ArrayList<>();
        System.out.println("Timer CREATED");
    }

    public void tick() throws InterruptedException {
        time++;
        System.out.println("Timer TICK, time:" + time);
        for (Transact t : transacts) {
            if (!t.getBlock().equals("Deleted") && !t.getBlock().equals("Free")) {
                t.incAge();
            }
        }
        Platform.runLater(() -> {
            monitorView.setTime(time);
            monitorView.reloadTransacts(transacts);
        });
        Thread.sleep(FIXED_RATE);
    }

    public int generateId() {
        return lastId++;
    }

    public int getTime() {
        return time;
    }

    public void register(Transact transact) {
        transacts.add(transact);
        System.out.println("Timer, Transact " + transact.getId() + " REGISTERED");
    }
}
