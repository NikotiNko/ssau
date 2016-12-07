package monitor.controller.component;

import javafx.application.Platform;
import monitor.controller.entity.Transact;
import monitor.view.MonitorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Таймер моделирования.
 */
public class Timer {
    private int fixedDelay;
    private MonitorView monitorView;
    private int time;
    private int lastId;
    private List<Transact> transacts;

    public Timer(MonitorView monitorView, int fixedDelay) {
        this.monitorView = monitorView;
        this.time = 0;
        this.lastId = 0;
        this.transacts = new ArrayList<>();
        this.fixedDelay = fixedDelay;
        System.out.println("Timer CREATED");
    }
    /**
     * Произвести вычислений на 1 тик моделируемого времени.
     * Увеличивает время пребывания каждого транзакта в модели.
     * Отрисовывает часы и таблицу транзактов.
     */
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
        Thread.sleep(fixedDelay);
    }

    /**
     * Генератор номера транзакта.
     */
    public int generateId() {
        return lastId++;
    }

    public int getTime() {
        return time;
    }

    /**
     * Регистрация созданного транзакта
     */
    public void register(Transact transact) {
        transacts.add(transact);
        System.out.println("Timer, Transact " + transact.getId() + " REGISTERED");
    }

    /**
     * Установка задержки системного времени.
     * @param fixedDelay значение в миллисекундах
     */
    public void setFixedDelay(int fixedDelay){
        this.fixedDelay = fixedDelay;
    }

    public List<Transact> getTransacts() {
        return transacts;
    }
}
