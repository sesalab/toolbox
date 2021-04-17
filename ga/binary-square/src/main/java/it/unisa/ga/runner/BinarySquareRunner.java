package it.unisa.ga.runner;

import it.unisa.ga.fitness.SquareFunction;
import it.unisa.ga.individual.SwitchSetting;
import it.unisa.ga.initializer.PopulationInitializer;
import it.unisa.ga.initializer.RandomUpperBoundedPopulationInitializer;
import it.unisa.ga.metaheuristic.SGA;
import it.unisa.ga.operator.crossover.SwitchSettingSinglePointCrossover;
import it.unisa.ga.operator.mutation.SwitchSettingSinglePointMutation;
import it.unisa.ga.operator.selection.RouletteWheelSelection;
import it.unisa.ga.results.Results;

import java.util.Map;

public class BinarySquareRunner extends Runner {
    public void run(Map<String, Double> input) throws CloneNotSupportedException {
        double numberOfIndividuals = input.get("numberOfIndividuals");
        double sizeOfIndividuals = input.get("sizeOfIndividuals");
        double mutationProbability = input.get("mutationProbability");
        double maxIterations = input.get("maxIterations");
        double maxIterationsNoImprovements = input.get("maxIterationsNoImprovements");

        SquareFunction fitnessFunction = new SquareFunction();
        PopulationInitializer<SwitchSetting> populationInitializer = new RandomUpperBoundedPopulationInitializer((int) numberOfIndividuals, (int) sizeOfIndividuals);
        RouletteWheelSelection<SwitchSetting> selectionOperator = new RouletteWheelSelection<>();
        SwitchSettingSinglePointCrossover crossoverOperator = new SwitchSettingSinglePointCrossover();
        SwitchSettingSinglePointMutation mutationOperator = new SwitchSettingSinglePointMutation();

        SGA<SwitchSetting> geneticAlgorithm = new SGA<>(fitnessFunction, populationInitializer,
                selectionOperator, crossoverOperator, mutationOperator, mutationProbability, (int) maxIterations, (int) maxIterationsNoImprovements);
        Results<SwitchSetting> results = geneticAlgorithm.run();
        SwitchSetting bestIndividual = results.getBestIndividual();
        results.getLog().forEach(System.out::println);
        System.out.printf("Search terminated in %d/%d iterations.%n", results.getNumberOfIterations(), geneticAlgorithm.getMaxIterations());
        System.out.printf("Best individual is %s, with fitness %.2f.%n", bestIndividual.getCoding(), bestIndividual.getFitness());
    }
}
