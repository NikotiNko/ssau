package monitor.controller.entity;


import monitor.controller.component.Timer;

import java.util.HashMap;
import java.util.Map;

public class Transact {
    private int id;
    private int birthDate;
    private int age;
    private int blockId;
    private Map<String, Integer> params;

    public Transact(Timer timer) {
        id = timer.generateId();
        birthDate = timer.getTime();
        age = 0;
        blockId = 0;
        timer.register(this);
        params = new HashMap<>();
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

    public int getBlockId() {
        return blockId;
    }

    public void incBlockId() {
        blockId++;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public Map<String, Integer> getParams() {
        return params;
    }
}
