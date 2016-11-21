package monitor.service.generator.impl;

import monitor.service.generator.RandomGenerator;

import java.util.Random;

public class RandomAdapter implements RandomGenerator {

    private Random rand;
    private double mx;
    private double dx;

    public RandomAdapter(double mx, double dx) {
        this.rand = new Random();
        this.mx = mx;
        this.dx = dx;
    }


    @Override
    public int generate() {
        return (int)Math.round(rand.nextDouble() * dx + mx);
    }
}
