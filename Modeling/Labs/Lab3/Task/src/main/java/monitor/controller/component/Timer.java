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
    private MonitorView monitorView;
    private int time;
    private int lastId;
    private List<Transact> transacts;

    public Timer(MonitorView monitorView) {
        this.monitorView = monitorView;
        this.time = 0;
        this.lastId = 0;
        this.transacts = new ArrayList<>();
    }

    public void tick() throws InterruptedException {
        time++;
        for (Transact t : transacts) {
            t.incAge();
        }
        Platform.runLater(()->{
            monitorView.setTime(time);
            monitorView.reloadTransacts(transacts);
        });
        Thread.sleep(1000);
    }

    public int generateId() {
        return lastId++;
    }

    public int getTime() {
        return time;
    }

    public void register(Transact transact) {
        transacts.add(transact);
    }
}
