package monitor.controller.component;

import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import monitor.controller.entity.Transact;
import monitor.service.generator.RandomGenerator;


/**
 * Класс устройства. Обрабатывает отправленные к нему транзакты.
 * Транзакты получаются из очереди путем вызова метода #advance(Transact) из очереди
 */
public class Device {

    private RandomGenerator randomGenerator;
    private int lastTime;
    private int lastGeneratedTime;
    private Transact currentTransact;
    private ProgressIndicator loader;

    public Device(RandomGenerator randomGenerator, ProgressIndicator loader) {
        this.randomGenerator = randomGenerator;
        this.lastGeneratedTime = 0;
        this.lastTime = lastGeneratedTime;
        this.loader = loader;
        System.out.println("Device CREATED");
    }

    /**
     * Проверка устройства на занятость
     */
    public boolean isBusy() {
        return lastTime > 0;
    }


    /**
     * Произвести вычислений на 1 тик моделируемого времени.
     * Уменьшает оставшееся время последнего транзакта. Если время вышло - транзакт освобождается из модели.
     */
    public void tick() {
        lastTime--;
        if (lastTime >= 0) {
            Platform.runLater(() -> loader.setProgress(1 - (double)lastTime / lastGeneratedTime));
        }
        System.out.println("Device TICK, last time:" + lastTime);
        if (lastTime == 0) {
            System.out.println("Device FREE, Transact " + currentTransact.getId() + " FREE");
            currentTransact.setBlock("Free");
            currentTransact = null;
        }
    }

    /**
     * Транзакт занимает устройство.
     * @param transact обрабатываемый транзакт.
     */
    public void advance(Transact transact) {
        System.out.println("Device ADVANCED");
        currentTransact = transact;
        lastGeneratedTime = randomGenerator.generate();
        lastTime = lastGeneratedTime;
    }
}
