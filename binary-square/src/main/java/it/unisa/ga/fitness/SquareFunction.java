package it.unisa.ga.fitness;

import it.unisa.ga.individuals.IntegerStringIndividual;

public class SquareFunction extends FitnessFunction<IntegerStringIndividual> {

    public SquareFunction() {
        super(true);
    }

    public void evaluate(IntegerStringIndividual individual) {
        int value = individual.decode();
        individual.setFitness(value * value);
    }
}
