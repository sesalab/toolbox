package it.unisa.ga.runner;

import it.unisa.ga.fitness.SquareFunction;
import it.unisa.ga.individual.SwitchSetting;
import it.unisa.ga.initializer.RandomUpperBoundedPopulationInitializer;
import it.unisa.ga.initializer.PopulationInitializer;
import it.unisa.ga.metaheuristic.SimpleGeneticAlgorithm;
import it.unisa.ga.operator.crossover.SwitchSettingSinglePointCrossover;
import it.unisa.ga.operator.mutation.SwitchSettingSinglePointMutation;
import it.unisa.ga.operator.selection.RouletteWheelSelection;
import it.unisa.ga.results.Results;

public class BinarySquareRunner extends Runner {
    public void run() throws CloneNotSupportedException {
        final int numberOfIndividuals = 4;
        final int sizeOfIndividuals = 5;
        final double mutationProbability = 0.5;
        final int maxIterations = 100;
        final int maxIterationsNoImprovements = 10; // Set this to 0 if you want to always reach maxIterations

        SquareFunction fitnessFunction = new SquareFunction();
        PopulationInitializer<SwitchSetting> populationInitializer = new RandomUpperBoundedPopulationInitializer(numberOfIndividuals, sizeOfIndividuals);
        RouletteWheelSelection<SwitchSetting> selectionOperator = new RouletteWheelSelection<>();
        SwitchSettingSinglePointCrossover crossoverOperator = new SwitchSettingSinglePointCrossover();
        SwitchSettingSinglePointMutation mutationOperator = new SwitchSettingSinglePointMutation();

        SimpleGeneticAlgorithm<SwitchSetting> geneticAlgorithm = new SimpleGeneticAlgorithm<>(fitnessFunction, populationInitializer,
                selectionOperator, crossoverOperator, mutationOperator, mutationProbability, maxIterations, maxIterationsNoImprovements);
        Results<SwitchSetting> results = geneticAlgorithm.run();
        SwitchSetting bestIndividual = results.getBestIndividual();
        results.getLog().forEach(System.out::println);
        System.out.printf("Search terminated in %d/%d iterations.%n", results.getNumberOfIterations(), geneticAlgorithm.getMaxIterations());
        System.out.printf("Best individual is %s, with fitness %.2f.%n", bestIndividual.getCoding(), bestIndividual.getFitness());
    }
}
