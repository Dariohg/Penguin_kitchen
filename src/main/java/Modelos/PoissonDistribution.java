package Modelos;

import java.util.Random;

public class PoissonDistribution {
    private final Random random;
    private final double lambda;

    public PoissonDistribution(double lambda) {
        this.lambda = lambda;
        this.random = new Random();
    }

    public int sample() {
        int count = 0;
        double L = Math.exp(-lambda);
        double p = 1.0;
        do {
            count++;
            p *= random.nextDouble();
        } while (p > L);
        return count - 1;
    }
}
