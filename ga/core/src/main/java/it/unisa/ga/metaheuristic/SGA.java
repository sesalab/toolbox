package it.unisa.ga.metaheuristic;

import it.unisa.ga.individual.Individual;
import it.unisa.ga.population.Population;
import it.unisa.ga.results.GAResults;

public class SGA<T extends Individual> extends GenerationalGA<T> {

    public SGA(GASetting<T> gaSetting) {
        super(gaSetting);
    }

    @Override
    public GAResults<T> run() throws CloneNotSupportedException {
        // Initialization of the first generation
        Population<T> firstGeneration = getPopulationInitializer().initialize();
        this.addGeneration(firstGeneration);
        // Evaluation
        this.getFitnessFunction().evaluate(firstGeneration);
        System.out.println("Gen 1) " + firstGeneration.getAverageFitness() + " (CurrentAvg)");
        int currentIteration = 1;
        do {
            Population<T> currentPopulation = getLastGeneration();
            // Selection
            Population<T> matingPool = getSelectionOperator().apply(currentPopulation);
            // Crossover
            Population<T> offsprings = getCrossoverOperator().apply(matingPool);
            // Mutation
            Population<T> newGeneration = getMutationOperator().apply(offsprings);
            // Confirm the new generation
            this.getFitnessFunction().evaluate(newGeneration);
            this.addGeneration(newGeneration);
            currentIteration++;
            System.out.println("Gen " + currentIteration + ") " + currentPopulation.getAverageFitness() + " (Best: " + currentPopulation.getBestIndividual().getFitness() + ")");
        }
        while (!getStoppingCondition().checkStop(this));
        return new GAResults<>(this);
    }
}
