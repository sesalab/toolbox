package it.unisa.ga.fitness;

import it.unisa.ga.individual.Switches;

public class SquareFunction extends FitnessFunction<Switches> {
    public SquareFunction() {
        super(true);
    }

    public void evaluate(Switches individual) {
        int value = individual.decode();
        individual.setFitness(value * value);
    }
}
