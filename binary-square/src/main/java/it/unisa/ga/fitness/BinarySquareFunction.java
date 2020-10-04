package it.unisa.ga.fitness;

import it.unisa.ga.individuals.BinaryIndividual;

// This function applies only on individuals represented as binary strings
public class BinarySquareFunction extends FitnessFunction<BinaryIndividual> {

    public BinarySquareFunction() {
        super(true);
    }

    protected void evaluate(BinaryIndividual individual) {
        // Note that decode() is only for BinaryStringIndividuals, not for every kind of Individuals
        double value = individual.decode();
        individual.setFitness(value * value);
    }
}
