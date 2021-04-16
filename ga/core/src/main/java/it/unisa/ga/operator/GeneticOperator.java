package it.unisa.ga.operator;

import it.unisa.ga.individual.Individual;
import it.unisa.ga.population.Population;

import java.util.Random;

public abstract class GeneticOperator<T extends Individual> {
    public abstract Population<T> apply(Population<T> population, Random rand) throws CloneNotSupportedException;
}
