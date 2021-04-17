package it.unisa.ga.setting;

import it.unisa.ga.fitness.FitnessFunction;
import it.unisa.ga.initializer.PopulationInitializer;
import it.unisa.ga.operator.crossover.CrossoverOperator;
import it.unisa.ga.operator.mutation.MutationOperator;
import it.unisa.ga.operator.selection.SelectionOperator;

public class GASetting {
    private final FitnessFunction<?> fitnessFunction;
    private final PopulationInitializer<?> populationInitializer;
    private final SelectionOperator<?> selectionOperator;
    private final CrossoverOperator<?> crossoverOperator;
    private final MutationOperator<?> mutationOperator;

    public GASetting(FitnessFunction<?> fitnessFunction, PopulationInitializer<?> populationInitializer, SelectionOperator<?> selectionOperator, CrossoverOperator<?> crossoverOperator, MutationOperator<?> mutationOperator) {
        this.fitnessFunction = fitnessFunction;
        this.populationInitializer = populationInitializer;
        this.selectionOperator = selectionOperator;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
    }

    public FitnessFunction<?> getFitnessFunction() {
        return fitnessFunction;
    }

    public PopulationInitializer<?> getPopulationInitializer() {
        return populationInitializer;
    }

    public SelectionOperator<?> getSelectionOperator() {
        return selectionOperator;
    }

    public CrossoverOperator<?> getCrossoverOperator() {
        return crossoverOperator;
    }

    public MutationOperator<?> getMutationOperator() {
        return mutationOperator;
    }
}
