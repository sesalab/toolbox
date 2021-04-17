package it.unisa.ga.runner;

import it.unisa.ga.fitness.SquareFunction;
import it.unisa.ga.individual.Switches;
import it.unisa.ga.population.initializer.PopulationInitializer;
import it.unisa.ga.population.initializer.RandomUpperBoundedPopulationInitializer;
import it.unisa.ga.metaheuristic.SGA;
import it.unisa.ga.operator.crossover.SwitchSettingSinglePointCrossover;
import it.unisa.ga.operator.mutation.SwitchSettingSinglePointMutation;
import it.unisa.ga.operator.selection.RouletteWheelSelection;
import it.unisa.ga.results.GAResults;
import it.unisa.ga.setting.GASetting;

import java.util.Map;

public class BinarySquareRunner extends Runner {
    public void run(Map<String, Double> input) throws CloneNotSupportedException {
        double numberOfIndividuals = input.get("numberOfIndividuals");
        double sizeOfIndividuals = input.get("sizeOfIndividuals");
        double mutationProbability = input.get("mutationProbability");
        double maxIterations = input.get("maxIterations");
        double maxIterationsNoImprovements = input.get("maxIterationsNoImprovements");

        SquareFunction fitnessFunction = new SquareFunction();
        PopulationInitializer<Switches> populationInitializer = new RandomUpperBoundedPopulationInitializer((int) numberOfIndividuals, (int) sizeOfIndividuals);
        RouletteWheelSelection<Switches> selectionOperator = new RouletteWheelSelection<>();
        SwitchSettingSinglePointCrossover crossoverOperator = new SwitchSettingSinglePointCrossover();
        SwitchSettingSinglePointMutation mutationOperator = new SwitchSettingSinglePointMutation();

        GASetting<Switches> gaSetting = new GASetting<>(fitnessFunction, populationInitializer, selectionOperator, crossoverOperator, mutationOperator);
        SGA<Switches> geneticAlgorithm = new SGA<>(gaSetting, mutationProbability, (int) maxIterations, (int) maxIterationsNoImprovements);
        GAResults<Switches> GAResults = geneticAlgorithm.run();
        Switches bestIndividual = GAResults.getBestIndividual();
        GAResults.getLog().forEach(System.out::println);
        System.out.printf("Search terminated in %d/%d iterations.%n", GAResults.getNumberOfIterations(), geneticAlgorithm.getMaxIterations());
        System.out.printf("Best individual is %s, with fitness %.2f.%n", bestIndividual.getCoding(), bestIndividual.getFitness());
    }
}
