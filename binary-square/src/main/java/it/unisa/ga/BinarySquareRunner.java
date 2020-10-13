package it.unisa.ga;

import it.unisa.ga.fitness.BinarySquareFunction;
import it.unisa.ga.fitness.FitnessFunction;
import it.unisa.ga.individuals.BinaryIndividual;
import it.unisa.ga.initializers.FixedSizeBinaryRandomInitializer;
import it.unisa.ga.initializers.Initializer;
import it.unisa.ga.metaheuritics.SimpleGeneticAlgorithm;
import it.unisa.ga.operators.crossover.CrossoverOperator;
import it.unisa.ga.operators.crossover.SinglePointCrossover;
import it.unisa.ga.operators.mutation.MutationOperator;
import it.unisa.ga.operators.mutation.SinglePointMutation;
import it.unisa.ga.operators.selection.RouletteWheelSelection;
import it.unisa.ga.operators.selection.SelectionOperator;
import it.unisa.ga.results.Results;

public class BinarySquareRunner {

    public static void main(String[] args) throws CloneNotSupportedException {
        final int numberOfIndividuals = 4;
        final int sizeOfIndividuals = 5;
        final int maxIterations = 100;
        final double mutationProbability = 0.5;
        final int maxIterationsNoImprovements = 5; // Set this to 0 if you want to always reach maxIterations

        FitnessFunction<BinaryIndividual> fitnessFunction = new BinarySquareFunction();
        Initializer<BinaryIndividual> initializer = new FixedSizeBinaryRandomInitializer(numberOfIndividuals, sizeOfIndividuals);
        SelectionOperator<BinaryIndividual> selectionOperator = new RouletteWheelSelection<>();
        CrossoverOperator<BinaryIndividual> crossoverOperator = new SinglePointCrossover();
        MutationOperator<BinaryIndividual> mutationOperator = new SinglePointMutation();

        SimpleGeneticAlgorithm<BinaryIndividual> geneticAlgorithm = new SimpleGeneticAlgorithm<>(fitnessFunction, initializer,
                selectionOperator, crossoverOperator, mutationOperator, mutationProbability, maxIterations, maxIterationsNoImprovements);
        Results<BinaryIndividual> results = geneticAlgorithm.run();

        BinaryIndividual bestIndividual = results.getBestIndividual();
        results.getLog().forEach(System.out::println);
        System.out.printf("Search terminated in %d/%d iterations.%n", results.getNumberOfIterations(), geneticAlgorithm.getMaxIterations());
        System.out.printf("Best individual is %s, with fitness %.2f.%n", bestIndividual.getCoding(), bestIndividual.getFitness());
    }
}
