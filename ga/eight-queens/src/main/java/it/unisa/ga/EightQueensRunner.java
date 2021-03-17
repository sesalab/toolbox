package it.unisa.ga;

import it.unisa.ga.fitness.ConflictsFunction;
import it.unisa.ga.individuals.ChessboardIndividual;
import it.unisa.ga.initializers.FixedSizeChessboardRandomInitializer;
import it.unisa.ga.metaheuristics.SimpleGeneticAlgorithm;
import it.unisa.ga.operators.crossover.ChessboardSinglePointCrossover;
import it.unisa.ga.operators.mutation.ChessboardSinglePointMutation;
import it.unisa.ga.operators.selection.RouletteWheelSelection;
import it.unisa.ga.results.Results;

import java.util.Arrays;

public class EightQueensRunner {

    public static void main(String[] args) throws CloneNotSupportedException {
        final int numberOfIndividuals = 1;
        final int chessSize = 8;
        final double mutationProbability = 1;
        final int maxIterations = 1000;
        final int maxIterationsNoImprovements = 0;

        ConflictsFunction fitnessFunction = new ConflictsFunction();
        FixedSizeChessboardRandomInitializer initializer = new FixedSizeChessboardRandomInitializer(numberOfIndividuals, chessSize);
        RouletteWheelSelection<ChessboardIndividual> selectionOperator = new RouletteWheelSelection<>();
        ChessboardSinglePointCrossover crossoverOperator = new ChessboardSinglePointCrossover();
        ChessboardSinglePointMutation mutationOperator = new ChessboardSinglePointMutation();

        SimpleGeneticAlgorithm<ChessboardIndividual> geneticAlgorithm = new SimpleGeneticAlgorithm<>(fitnessFunction, initializer,
                selectionOperator, crossoverOperator, mutationOperator, mutationProbability, maxIterations, maxIterationsNoImprovements);
        Results<ChessboardIndividual> results = geneticAlgorithm.run();
        ChessboardIndividual bestIndividual = results.getBestIndividual();
        results.getLog().forEach(System.out::println);
        System.out.printf("Search terminated in %d/%d iterations.%n", results.getNumberOfIterations(), geneticAlgorithm.getMaxIterations());
        System.out.printf("Best individual is %s, with fitness %.2f.%n", Arrays.toString(bestIndividual.getCoding()), bestIndividual.getFitness());
    }

}
