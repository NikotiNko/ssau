package monitor.controller.entity;


import monitor.controller.component.Timer;

import java.util.HashMap;
import java.util.Map;

public class Transact {
    private int id;
    private int birthDate;
    private int age;
    private String block;
    private Map<String, Integer> params;

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
