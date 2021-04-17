package it.unisa.ga.individual.generator;

import it.unisa.ga.individual.Individual;

public abstract class IndividualGenerator<T extends Individual> {
    public abstract T generateIndividual();
}
