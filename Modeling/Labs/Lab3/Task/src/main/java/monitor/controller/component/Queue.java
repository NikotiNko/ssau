package monitor.controller.component;

import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import monitor.controller.entity.Transact;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Очередь устройства.
 */
public class Queue {

    private java.util.Queue<Transact> queue = new LinkedList<Transact>();
    private int queueLen;
    private int queueMaxTime;
    private Device device;
    private ProgressIndicator loader;

    /**
     * Счетчики удаленных транзактов
     */
    private int deletedByTime;
    private int deletedBySize;

    public Queue(int queueLen, int queueMaxTime, Device device, ProgressIndicator loader) {
        this.queueLen = queueLen;
        this.queueMaxTime = queueMaxTime;
        this.device = device;
        this.loader = loader;
        deletedByTime = 0;
        deletedBySize = 0;
        System.out.println("Queue CREATED");
    }

    /**
     * Проверка очереди на пустоту
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Произвести вычислений на 1 тик моделируемого времени.
     * Увеличивает время пребывания транзактов в очереди.
     * Если время пребывания достигло максимума - удаляет транзакт из модели.
     * Если устройство свободно - отправлят транзакт в устройство.
     */
    public void tick() {
        System.out.println("Queue TICK, queue size:" + queue.size());
        Iterator<Transact> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Transact t = iterator.next();
            Integer time = t.getParams().get("queueTime");
            if (time > queueMaxTime) {
                iterator.remove();
                t.setBlock("Deleted");
                refresh();
                deletedByTime++;
                System.out.println("Transact" + t.getId() + " DELETED FROM QUEUE, out of time!");
            } else {
                time++;
                t.getParams().put("queueTime", time);
            }
        }
        if (!device.isBusy() && queue.size() != 0) {
            Transact polled = queue.poll();
            refresh();
            polled.setBlock("Device");
            device.advance(polled);
            System.out.println("Transact" + polled.getId() + " GO TO DEVICE");
        }
    }

    /**
     * Добавить транзакт в очередь. Если очередь заполненна, транзакт удаляется из модели.
     * @param transact
     */
    public void add(Transact transact) {
        System.out.println("Transact" + transact.getId() + " OFFER TO QUEUE");
        if (queue.size() < queueLen) {
            transact.getParams().put("queueTime", 0);
            queue.offer(transact);
            refresh();
            System.out.println("Transact" + transact.getId() + " ADDED TO QUEUE");
        } else {
            transact.setBlock("Deleted");
            deletedBySize++;
            System.out.println("Transact DELETED FROM QUEUE, queue overload!");
        }
    }

    /**
     * Отрисовка загрузки.
     */
    private void refresh() {
        Platform.runLater(() -> loader.setProgress((double) queue.size() / queueLen));
    }

    public int getDeletedByTime() {
        return deletedByTime;
    }

    public int getDeletedBySize() {
        return deletedBySize;
    }
}
