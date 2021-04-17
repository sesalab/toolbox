package it.unisa.ga.metaheuristic;

import it.unisa.ga.fitness.FitnessFunction;
import it.unisa.ga.individual.Individual;
import it.unisa.ga.population.initializer.PopulationInitializer;
import it.unisa.ga.operator.crossover.CrossoverOperator;
import it.unisa.ga.operator.mutation.MutationOperator;
import it.unisa.ga.operator.selection.SelectionOperator;
import it.unisa.ga.results.GAResults;
import it.unisa.ga.setting.GASetting;

public abstract class GeneticAlgorithm<T extends Individual> {
    private final GASetting<T> gaSetting;

    public GeneticAlgorithm(GASetting<T> gaSetting) {
        this.gaSetting = gaSetting;
    }

    public abstract GAResults<T> run() throws CloneNotSupportedException;

    public FitnessFunction<T> getFitnessFunction() {
        return gaSetting.getFitnessFunction();
    }

    public PopulationInitializer<T> getPopulationInitializer() {
        return gaSetting.getPopulationInitializer();
    }

    public SelectionOperator<T> getSelectionOperator() {
        return gaSetting.getSelectionOperator();
    }

    public CrossoverOperator<T> getCrossoverOperator() {
        return gaSetting.getCrossoverOperator();
    }

    public MutationOperator<T> getMutationOperator() {
        return gaSetting.getMutationOperator();
    }

    public GASetting<T> getGaSetting() {
        return gaSetting;
    }
}
