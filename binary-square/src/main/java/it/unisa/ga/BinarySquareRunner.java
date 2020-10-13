package it.unisa.ga;

import it.unisa.ga.fitness.SquareFunction;
import it.unisa.ga.individuals.IntegerStringIndividual;
import it.unisa.ga.initializers.FixedSizeIntegerStringRandomInitializer;
import it.unisa.ga.initializers.Initializer;
import it.unisa.ga.metaheuritics.SimpleGeneticAlgorithm;
import it.unisa.ga.operators.crossover.IntegerStringSinglePointCrossover;
import it.unisa.ga.operators.mutation.IntegerStringSinglePointMutation;
import it.unisa.ga.operators.selection.RouletteWheelSelection;
import it.unisa.ga.results.Results;

public class BinarySquareRunner {

    public static void main(String[] args) throws CloneNotSupportedException {
        final int numberOfIndividuals = 4;
        final int sizeOfIndividuals = 5;
        final double mutationProbability = 0.5;
        final int maxIterations = 100;
        final int maxIterationsNoImprovements = 10; // Set this to 0 if you want to always reach maxIterations

        SquareFunction fitnessFunction = new SquareFunction();
        Initializer<IntegerStringIndividual> initializer = new FixedSizeIntegerStringRandomInitializer(numberOfIndividuals, sizeOfIndividuals);
        RouletteWheelSelection<IntegerStringIndividual> selectionOperator = new RouletteWheelSelection<>();
        IntegerStringSinglePointCrossover crossoverOperator = new IntegerStringSinglePointCrossover();
        IntegerStringSinglePointMutation mutationOperator = new IntegerStringSinglePointMutation();

        SimpleGeneticAlgorithm<IntegerStringIndividual> geneticAlgorithm = new SimpleGeneticAlgorithm<>(fitnessFunction, initializer,
                selectionOperator, crossoverOperator, mutationOperator, mutationProbability, maxIterations, maxIterationsNoImprovements);
        Results<IntegerStringIndividual> results = geneticAlgorithm.run();
        IntegerStringIndividual bestIndividual = results.getBestIndividual();
        results.getLog().forEach(System.out::println);
        System.out.printf("Search terminated in %d/%d iterations.%n", results.getNumberOfIterations(), geneticAlgorithm.getMaxIterations());
        System.out.printf("Best individual is %s, with fitness %.2f.%n", bestIndividual.getCoding(), bestIndividual.getFitness());
    }
}
