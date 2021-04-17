package it.unisa.ga.fitness;

import it.unisa.ga.individual.BinaryIndividual;

public class SquareFunction extends FitnessFunction<BinaryIndividual> {
    public SquareFunction() {
        super(true);
    }

    public void evaluate(BinaryIndividual individual) {
        int value = individual.decode();
        individual.setFitness(value * value);
    }
}
