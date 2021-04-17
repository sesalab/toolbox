package it.unisa.ga.setting;

import it.unisa.ga.fitness.FitnessFunction;
import it.unisa.ga.initializer.PopulationInitializer;
import it.unisa.ga.operator.crossover.CrossoverOperator;
import it.unisa.ga.operator.mutation.MutationOperator;
import it.unisa.ga.operator.selection.SelectionOperator;

public class SGASetting extends GASetting {


    public SGASetting(FitnessFunction<?> fitnessFunction, PopulationInitializer<?> populationInitializer, SelectionOperator<?> selectionOperator, CrossoverOperator<?> crossoverOperator, MutationOperator<?> mutationOperator) {
        super(fitnessFunction, populationInitializer, selectionOperator, crossoverOperator, mutationOperator);
    }
}
