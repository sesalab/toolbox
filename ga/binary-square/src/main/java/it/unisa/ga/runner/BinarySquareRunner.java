package it.unisa.ga.runner;

import it.unisa.ga.fitness.SquareFunction;
import it.unisa.ga.individual.Switches;
import it.unisa.ga.metaheuristic.GenerationalGA;
import it.unisa.ga.metaheuristic.SGA;
import it.unisa.ga.operator.crossover.SwitchSettingSinglePointCrossover;
import it.unisa.ga.operator.mutation.SwitchSettingSinglePointMutation;
import it.unisa.ga.operator.selection.RouletteWheelSelection;
import it.unisa.ga.population.initializer.PopulationInitializer;
import it.unisa.ga.population.initializer.RandomUpperBoundedPopulationInitializer;
import it.unisa.ga.results.GAResults;
import it.unisa.ga.stopping.MaxIterationsStoppingCondition;
import it.unisa.ga.stopping.MaxNoImprovementsStoppingConditions;
import it.unisa.ga.stopping.MultipleStoppingCondition;

import java.util.Map;

public class BinarySquareRunner extends Runner {
    public void run(Map<String, Double> input) throws CloneNotSupportedException {
        int numberOfIndividuals = input.get("numberOfIndividuals").intValue();
        int sizeOfIndividuals = input.get("sizeOfIndividuals").intValue();
        int maxIterations = input.get("maxIterations").intValue();
        int maxIterationsNoImprovements = input.get("maxIterationsNoImprovements").intValue();
        double mutationProbability = input.get("mutationProbability");

        SquareFunction fitnessFunction = new SquareFunction();
        PopulationInitializer<Switches> populationInitializer = new RandomUpperBoundedPopulationInitializer(numberOfIndividuals, sizeOfIndividuals);
        RouletteWheelSelection<Switches> selectionOperator = new RouletteWheelSelection<>();
        SwitchSettingSinglePointCrossover crossoverOperator = new SwitchSettingSinglePointCrossover();
        SwitchSettingSinglePointMutation mutationOperator = new SwitchSettingSinglePointMutation();
        MultipleStoppingCondition stoppingCondition = new MultipleStoppingCondition();
        stoppingCondition.add(new MaxIterationsStoppingCondition(maxIterations));
        stoppingCondition.add(new MaxNoImprovementsStoppingConditions(maxIterationsNoImprovements));

        GenerationalGA.GASetting<Switches> gaSetting = new GenerationalGA.GASetting<>(fitnessFunction, populationInitializer,
                selectionOperator, crossoverOperator, mutationOperator, stoppingCondition);
        SGA<Switches> geneticAlgorithm = new SGA<>(gaSetting, mutationProbability);
        GAResults<Switches> gaResults = geneticAlgorithm.run();
        Switches bestIndividual = gaResults.getBestIndividual();
        System.out.printf("Search terminated in %d/%d iterations.%n", gaResults.getNumberOfIterations(), maxIterations);
        System.out.printf("Best individual is %s, with fitness %f.%n", bestIndividual.getCoding(), bestIndividual.getFitness());
    }
}
