package it.unisa.ga.population.initializer;

import it.unisa.ga.individual.Individual;

public abstract class IndividualGenerator<T extends Individual> {
    public abstract T generateIndividual();
}
