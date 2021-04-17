package it.unisa.ga.operator;

import it.unisa.ga.individual.Individual;
import it.unisa.ga.population.Population;

import java.util.Random;

public abstract class GeneticOperator<T extends Individual> {
    private final Random random;

    public abstract Population<T> apply(Population<T> population) throws CloneNotSupportedException;

    public GeneticOperator(Random random) {
        this.random = random;
    }

    public Random getRandom() {
        return random;
    }
}
