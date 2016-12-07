package monitor.controller;


import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import monitor.controller.component.Device;
import monitor.controller.component.Generator;
import monitor.controller.component.Queue;
import monitor.controller.component.Timer;
import monitor.controller.entity.Result;
import monitor.controller.entity.Transact;
import monitor.service.generator.RandomGenerator;
import monitor.view.MonitorView;

import java.util.List;

/**
 * Контроллер системы моделирования.
 */
public class MonitorController {

    private Device device;
    private Generator generator;
    private Queue queue;
    private Timer timer;
    private boolean isInterrupted;
    private MonitorView view;

    public MonitorController(
            MonitorView monitorView,
            RandomGenerator generateRandomGenerator,
            RandomGenerator advanceRandomGenerator,
            int queueLen,
            int queueMaxTime,
            int transactCount,
            int fixedDelay,
            ProgressIndicator generatorLoader,
            ProgressIndicator deviceLoader,
            ProgressIndicator queueLoader
    ) {
        this.device = new Device(advanceRandomGenerator, deviceLoader);
        this.queue = new Queue(queueLen, queueMaxTime, device, queueLoader);
        this.timer = new Timer(monitorView, fixedDelay);
        this.generator = new Generator(generateRandomGenerator, transactCount, queue, timer, generatorLoader);
        this.view = monitorView;
    }

    /**
     * Начало моделирования.
     * Каждому блоку в цикле передается по 1 ед. моделируемого времени.
     * Как только все блоки освободились - моделирование заканчивается,
     * начинается генерирование результата.
     * isInterrupted - используется для остановки моделирования.
     * @see #stop()
     */
    public void start() throws InterruptedException {
        while ((generator.hasNext() || !queue.isEmpty() || device.isBusy()) && (!isInterrupted)) {
            generator.tick();
            queue.tick();
            device.tick();
            timer.tick();
        }
        List<Transact> transacts = timer.getTransacts();
        int allTransactsCount = transacts.size();
        long deletedTransactCount = queue.getDeletedBySize() + queue.getDeletedByTime();
        Platform.runLater(() ->
                view.viewResult(
                        Result.build()
                                .text("Результаты:")
                                .text("Всего транзактов: " + (allTransactsCount - 1))
                                .text("Потеряно транзактов: " + deletedTransactCount)
                                .text("Из-за времени пребывания: " + queue.getDeletedByTime())
                                .text("Из-за переполнения очереди: " + queue.getDeletedBySize())
                                .text("Вероятность потери транзакта: " + Math.floor((((double) deletedTransactCount / allTransactsCount) * 10000)) / 100 + "%")
                ));
    }

    /**
     * Остановка моделирования.
     */
    public void stop() {
        isInterrupted = true;
    }

    /**
     * Установка задержки системного времени.
     * @param delay значение в миллисекундах
     */
    public void setFixedDelay(int delay) {
        timer.setFixedDelay(delay);
    }
}
