package it.unisa.ga.initializers;

import it.unisa.ga.individuals.Individual;
import it.unisa.ga.populations.Population;

public abstract class Initializer<T extends Individual> {

    public abstract Population<T> initialize();

}
