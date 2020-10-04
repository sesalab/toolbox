package it.unisa.ga.populations;

import it.unisa.ga.individuals.Individual;

import java.util.HashSet;

public class Population<T extends Individual> extends HashSet<T> {

    private T bestIndividual;

    public Population() {
        this.bestIndividual = null;
    }

    public T getBestIndividual() {
        return bestIndividual;
    }

    public void setBestIndividual(T bestIndividual) {
        this.bestIndividual = bestIndividual;
    }

    public double getAverageFitness() {
        return super.stream()
                .mapToDouble(Individual::getFitness)
                .average().orElse(0.0);
    }
}
