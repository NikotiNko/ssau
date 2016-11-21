package monitor.service.generator.impl;

import monitor.service.generator.RandomGenerator;

import java.util.Random;


public class Gaussian implements RandomGenerator {

    private Random rand;
    private double mx;
    private double dx;

    public Gaussian(double mx, double dx) {
        this.rand = new Random();
        this.mx = mx;
        this.dx = dx;
    }

    @Override
    public int generate() {
        double result = 0;
        for (int i = 0; i < 12; i++) {
            result += rand.nextDouble();
        }

        double res = (result - 6) * Math.sqrt(dx) + mx;
        if (res >= 0) {
            return (int)Math.round(res);
        } else {
            return generate();
        }
    }
}
