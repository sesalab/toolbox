package it.unisa.ga;

import it.unisa.ga.fitness.ConflictsFunction;
import it.unisa.ga.individual.ChessboardIndividual;
import it.unisa.ga.metaheuristic.GenerationalGA;
import it.unisa.ga.population.initializer.FixedSizeChessboardRandomInitializer;
import it.unisa.ga.metaheuristic.SGA;
import it.unisa.ga.operator.crossover.ChessboardSinglePointCrossover;
import it.unisa.ga.operator.mutation.ChessboardSinglePointMutation;
import it.unisa.ga.operator.selection.RouletteWheelSelection;
import it.unisa.ga.results.GAResults;
import it.unisa.ga.stopping.MaxIterationsStoppingCondition;
import it.unisa.ga.stopping.MaxNoImprovementsStoppingConditions;
import it.unisa.ga.stopping.MultipleStoppingCondition;

import java.util.Arrays;
import java.util.Random;

public class EightQueensRunner {

    public static void main(String[] args) throws CloneNotSupportedException {
        final int numberOfIndividuals = 1;
        final int chessSize = 8;
        final double mutationProbability = 1;
        final double crossOverProbability = 1;
        final int maxIterations = 1000;
        final int maxIterationsNoImprovements = 0;
        final Random random = new Random();

        ConflictsFunction<ChessboardIndividual> fitnessFunction = new ConflictsFunction<>();
        FixedSizeChessboardRandomInitializer initializer = new FixedSizeChessboardRandomInitializer(numberOfIndividuals, chessSize);
        RouletteWheelSelection<ChessboardIndividual> selectionOperator = new RouletteWheelSelection<>(random);
        ChessboardSinglePointCrossover<ChessboardIndividual> crossoverOperator = new ChessboardSinglePointCrossover<>(crossOverProbability, random);
        ChessboardSinglePointMutation<ChessboardIndividual> mutationOperator = new ChessboardSinglePointMutation<>(mutationProbability, random);

        //Get Multiple Stopping conditions
        MultipleStoppingCondition multipleStoppingConditions = getMultipleStoppingCondition(maxIterationsNoImprovements, maxIterations);

        GenerationalGA.GASetting<ChessboardIndividual> settings = new GenerationalGA.GASetting<>(fitnessFunction, initializer, selectionOperator, crossoverOperator, mutationOperator, multipleStoppingConditions);

        SGA<ChessboardIndividual> geneticAlgorithm = new SGA<>(settings);
        GAResults<ChessboardIndividual> GAResults = geneticAlgorithm.run();
        ChessboardIndividual bestIndividual = GAResults.getBestIndividual();
        //GAResults.getLog().forEach(System.out::println); nonexistent method
        System.out.printf("Search terminated in %d/%d iterations.%n", GAResults.getNumberOfIterations(),maxIterations);
        System.out.printf("Best individual is %s, with fitness %.2f.%n", Arrays.toString(bestIndividual.getEncoding()), bestIndividual.getFitness());
    }



    private static MultipleStoppingCondition getMultipleStoppingCondition(int maxIterationsNoImprovements, int maxIterations) {
        MaxNoImprovementsStoppingConditions stoppingConditionNoImprov = new MaxNoImprovementsStoppingConditions(maxIterationsNoImprovements);

        //Set second stopping condition to maxIterations number
        MaxIterationsStoppingCondition stoppingConditionMaxIt = new MaxIterationsStoppingCondition(maxIterations);
        //Set stopping condition: how many gen with no improvement before stop = max(maxIterationsNoImprovement, 1)
        MultipleStoppingCondition multipleStoppingConditions = new MultipleStoppingCondition();

        multipleStoppingConditions.add(stoppingConditionNoImprov);
        multipleStoppingConditions.add(stoppingConditionMaxIt);
        return multipleStoppingConditions;
    }

}
