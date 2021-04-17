package it.unisa.ga.setting;

import it.unisa.ga.fitness.FitnessFunction;
import it.unisa.ga.individual.Individual;
import it.unisa.ga.population.initializer.PopulationInitializer;
import it.unisa.ga.operator.crossover.CrossoverOperator;
import it.unisa.ga.operator.mutation.MutationOperator;
import it.unisa.ga.operator.selection.SelectionOperator;

// TODO Add Stopping Condition
public class GASetting<T extends Individual> {
    private final FitnessFunction<T> fitnessFunction;
    private final PopulationInitializer<T> populationInitializer;
    private final SelectionOperator<T> selectionOperator;
    private final CrossoverOperator<T> crossoverOperator;
    private final MutationOperator<T> mutationOperator;

    public GASetting(FitnessFunction<T> fitnessFunction, PopulationInitializer<T> populationInitializer, SelectionOperator<T> selectionOperator,
                     CrossoverOperator<T> crossoverOperator, MutationOperator<T> mutationOperator) {
        this.fitnessFunction = fitnessFunction;
        this.populationInitializer = populationInitializer;
        this.selectionOperator = selectionOperator;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
    }

    public FitnessFunction<T> getFitnessFunction() {
        return fitnessFunction;
    }

    public PopulationInitializer<T> getPopulationInitializer() {
        return populationInitializer;
    }

    public SelectionOperator<T> getSelectionOperator() {
        return selectionOperator;
    }

    public CrossoverOperator<T> getCrossoverOperator() {
        return crossoverOperator;
    }

    public MutationOperator<T> getMutationOperator() {
        return mutationOperator;
    }
}
