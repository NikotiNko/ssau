package monitor.controller.entity;


import monitor.controller.component.Timer;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс транзакта.
 */
public class Transact {
    private int id;
    /**
     * Время появления.
     */
    private int birthDate;
    /**
     * Время пребывания в модели.
     */
    private int age;
    /**
     * Текущий блок.
     */
    private String block;
    /**
     * Параметры транзакта.
     */
    private Map<String, Integer> params;

    /**
     * При создании транзакт регистрирует себя в таймере.
     * @param timer
     */
    public Transact(Timer timer) {
        id = timer.generateId();
        birthDate = timer.getTime();
        age = 0;
        block = "Generator";
        params = new HashMap<>();
        timer.register(this);
        System.out.println("Transact " + id + " CREATED");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return age;
    }

    public void incAge() {
        age++;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public Map<String, Integer> getParams() {
        return params;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return "Transact{" +
                "id=" + id +
                ", birthDate=" + birthDate +
                ", age=" + age +
                ", block=" + block +
                ", params=" + params +
                '}';
    }
}
