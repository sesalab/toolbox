package it.unisa.ga.metaheuristic;

import it.unisa.ga.individual.Individual;
import it.unisa.ga.population.Population;
import it.unisa.ga.results.GAResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SGA<T extends Individual> extends GenerationalGA<T> {
    // We set probability of doing a mutation. If provided invalid, 0.1 is set.
    private final double mutationProbability;

    public SGA(GASetting<T> gaSetting, double mutationProbability) {
        super(gaSetting);
        if (0.0 <= mutationProbability && mutationProbability <= 1.0) {
            this.mutationProbability = mutationProbability;
        } else {
            this.mutationProbability = 0.1;
        }
    }

    // Method with no side effects
    public GAResults<T> run() throws CloneNotSupportedException {
        Random rand = new Random();

        // Initialization of the first generation
        Population<T> firstGeneration = getPopulationInitializer().initialize();
        addGeneration(firstGeneration);

        // Evaluation
        getFitnessFunction().evaluate(firstGeneration);
        System.out.println("Gen 1) " + firstGeneration.getAverageFitness() + " (CurrentAvg)");

        int currentIteration = 1;
        do {
            Population<T> currentPopulation = getLastGeneration();

            // Selection
            Population<T> matingPool = getSelectionOperator().apply(currentPopulation, rand);

            // Crossover
            Population<T> offsprings = getCrossoverOperator().apply(matingPool, rand);

            //TODO Where to move this probability logic? Probabbly when creating it in BinarySquareRunner. Same as crossover prob.

            // Mutation
            Population<T> newGeneration = offsprings;
            if (rand.nextDouble() <= mutationProbability) {
                newGeneration = getMutationOperator().apply(offsprings, rand);
            }

            // Confirm the new generation
            getFitnessFunction().evaluate(newGeneration);
            addGeneration(newGeneration);
            currentIteration++;

            System.out.println("Gen " + currentIteration + ") " + currentPopulation.getAverageFitness() + " (Best: " + currentPopulation.getBestIndividual().getFitness() + ")");
        }
        while (!getStoppingCondition().checkStop(this));
        return new GAResults<>(this);
    }
}
