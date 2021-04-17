package it.unisa.ga.results;

import it.unisa.ga.individual.Individual;
import it.unisa.ga.metaheuristic.GenerationalGA;
import it.unisa.ga.population.Population;

import java.util.List;

public class GAResults<T extends Individual> {
    private final GenerationalGA<T> ga;

    public GAResults(GenerationalGA<T> ga) {
        this.ga = ga;
    }

    public GenerationalGA<T> getGa() {
        return ga;
    }

    public Population<T> getBestGeneration() {
        return ga.getBestGeneration();
    }

    public T getBestIndividual() {
        return getBestGeneration().getBestIndividual();
    }

    public int getNumberOfIterations() {
        return getGa().getNumberOfGenerations();
    }
}
