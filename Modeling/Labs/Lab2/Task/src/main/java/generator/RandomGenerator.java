package generator;

import java.util.Random;

/**
 * Created by Артем on 06.11.2016.
 */
public class RandomGenerator {
    Random rand;

    public RandomGenerator()
    {
        rand = new Random();
    }
    public double generate(double m, double d)
    {
        double result = 0;
        for (int i = 0; i < 12; i++)
        {
            result += rand.nextDouble();
        }

        double res = (result - 6) * Math.sqrt(d) + m;
        if (res>=0){
            return res;
        }else {
            return generate(m, d);
        }
    }
}
