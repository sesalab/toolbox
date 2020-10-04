package it.unisa.ga.metaheuritics;

import it.unisa.ga.fitness.FitnessFunction;
import it.unisa.ga.individuals.Individual;
import it.unisa.ga.initializers.Initializer;
import it.unisa.ga.operators.crossover.CrossoverOperator;
import it.unisa.ga.operators.mutation.MutationOperator;
import it.unisa.ga.operators.selection.SelectionOperator;
import it.unisa.ga.populations.Population;

import java.util.Random;
import java.util.Stack;

public class SimpleGeneticAlgorithm<T extends Individual> extends GeneticAlgorithm<T> {

    // We decide to keep the history of all generations
    private final Stack<Population<T>> generations;
    // We set probability of doing a mutation. If provided invalid, 0.1 is set.
    private final double mutationProbability;

    // One of the stopping condition is reaching the maximum number of allowed iterations (at least > 0)
    private final int maxIterations;
    // Early stop if there are no improvements in X generations
    private final int maxIterationsWithoutImprovements;

    public SimpleGeneticAlgorithm(FitnessFunction<T> fitnessFunction, Initializer<T> initializer,
                                  SelectionOperator<T> selectionOperator, CrossoverOperator<T> crossoverOperator,
                                  MutationOperator<T> mutationOperator, double mutationProbability,
                                  int maxIterations, int maxIterationsWithoutImprovements) {
        super(fitnessFunction, initializer, selectionOperator, crossoverOperator, mutationOperator);
        this.generations = new Stack<>();
        if (0.0 <= mutationProbability && mutationProbability <= 1.0) {
            this.mutationProbability = mutationProbability;
        } else {
            this.mutationProbability = 0.1;
        }
        this.maxIterations = Math.max(maxIterations, 1);
        this.maxIterationsWithoutImprovements = Math.max(maxIterationsWithoutImprovements, 0);
    }

    public T run() throws CloneNotSupportedException {
        Random rand = new Random();
        // Initialization of the first generation
        generations.push(getInitializator().initialize());
        Population<T> firstGeneration = generations.peek();
        getFitnessFunction().evaluate(firstGeneration);
        Population<T> bestGeneration = firstGeneration;

        int iterations = 0;
        int iterationsWithoutImprovements = 0;
        do {
            Population<T> currentGeneration = generations.peek();

            // Selection
            Population<T> matingPool = getSelectionOperator().apply(currentGeneration, rand);
            // Crossover
            Population<T> offsprings = getCrossoverOperator().apply(matingPool, rand);
            // Mutation
            Population<T> newGeneration = offsprings;
            if (rand.nextDouble() <= mutationProbability) {
                newGeneration = getMutationOperator().apply(offsprings, rand);
            }
            getFitnessFunction().evaluate(newGeneration);
            generations.push(newGeneration);
            iterations++;

            // Check if there are average improvements
            double bestAverageFitness = bestGeneration.getAverageFitness();
            double newAverageFitness = newGeneration.getAverageFitness();
            System.out.println("New: " + newAverageFitness + " vs Best: " + bestAverageFitness);
            if (newAverageFitness > bestAverageFitness) {
                bestGeneration = newGeneration;
                iterationsWithoutImprovements = 0;
                System.out.println("Improvement");
            } else {
                iterationsWithoutImprovements++;
                // Check if there is a limit of no improvements and this limit is exceeded
                if (0 < maxIterationsWithoutImprovements && maxIterationsWithoutImprovements <= iterationsWithoutImprovements) {
                    System.out.println("Early Stop");
                    break;
                }
            }
        } while (iterations < maxIterations);
        // TODO Return a Result object that contain everything for a good report: generation stack, best generation, etc.
        return bestGeneration.getBestIndividual();
    }
}
