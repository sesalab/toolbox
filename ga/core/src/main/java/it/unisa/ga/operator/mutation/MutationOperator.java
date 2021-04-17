package it.unisa.ga.operator.mutation;

import it.unisa.ga.individual.Individual;
import it.unisa.ga.operator.GeneticOperator;
import it.unisa.ga.population.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class MutationOperator<T extends Individual> extends GeneticOperator<T> {
    private final double mutationProbability;

    public MutationOperator(double mutationProbability, Random random) {
        super(random);
        if (0.0 <= mutationProbability && mutationProbability <= 1.0) {
            this.mutationProbability = mutationProbability;
        } else {
            this.mutationProbability = 0.1;
        }
    }

    public Population<T> apply(Population<T> population) throws CloneNotSupportedException {
        if (getRandom().nextDouble() >= mutationProbability) {
            return population;
        }
        List<T> populationList = new ArrayList<>(population);
        T chosenIndividual = populationList.get(getRandom().nextInt(populationList.size()));
        T mutatedIndividual = mutate(chosenIndividual);
        populationList.remove(chosenIndividual);
        populationList.add(mutatedIndividual);

        Population<T> newPopulation = population.clone();
        newPopulation.setId(population.getId() + 1);
        newPopulation.clear();
        newPopulation.addAll(populationList);
        return newPopulation;
    }

    protected abstract T mutate(T individual) throws CloneNotSupportedException;

    public double getMutationProbability() {
        return mutationProbability;
    }
}
