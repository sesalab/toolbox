package it.unisa.ga.operators;

import it.unisa.ga.individuals.Individual;
import it.unisa.ga.populations.Population;

import java.util.Random;

public abstract class GeneticOperator<T extends Individual> {

    public abstract Population<T> apply(Population<T> population, Random rand) throws CloneNotSupportedException;

}
