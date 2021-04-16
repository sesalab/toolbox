package it.unisa.ga.metaheuristic;

import it.unisa.ga.fitness.FitnessFunction;
import it.unisa.ga.individual.Individual;
import it.unisa.ga.initializer.PopulationInitializer;
import it.unisa.ga.operator.crossover.CrossoverOperator;
import it.unisa.ga.operator.mutation.MutationOperator;
import it.unisa.ga.operator.selection.SelectionOperator;
import it.unisa.ga.results.Results;

public abstract class GeneticAlgorithm<T extends Individual> {
    private final FitnessFunction<T> fitnessFunction;
    private final PopulationInitializer<T> populationInitializer;
    private final SelectionOperator<T> selectionOperator;
    private final CrossoverOperator<T> crossoverOperator;
    private final MutationOperator<T> mutationOperator;

    public GeneticAlgorithm(FitnessFunction<T> fitnessFunction, PopulationInitializer<T> populationInitializer,
                            SelectionOperator<T> selectionOperator, CrossoverOperator<T> crossoverOperator,
                            MutationOperator<T> mutationOperator) {
        this.fitnessFunction = fitnessFunction;
        this.populationInitializer = populationInitializer;
        this.selectionOperator = selectionOperator;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
    }

    public abstract Results<T> run() throws CloneNotSupportedException;

    public FitnessFunction<T> getFitnessFunction() {
        return fitnessFunction;
    }

    public PopulationInitializer<T> getInitializer() {
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
