package it.unisa.ga;

import it.unisa.ga.fitness.BinarySquareFunction;
import it.unisa.ga.fitness.FitnessFunction;
import it.unisa.ga.individuals.BinaryIndividual;
import it.unisa.ga.initializers.FixedSizeBinaryRandomInitializer;
import it.unisa.ga.initializers.Initializer;
import it.unisa.ga.metaheuritics.GeneticAlgorithm;
import it.unisa.ga.metaheuritics.SimpleGeneticAlgorithm;
import it.unisa.ga.operators.crossover.CrossoverOperator;
import it.unisa.ga.operators.crossover.SinglePointCrossover;
import it.unisa.ga.operators.mutation.MutationOperator;
import it.unisa.ga.operators.mutation.SinglePointMutation;
import it.unisa.ga.operators.selection.RouletteWheelSelection;
import it.unisa.ga.operators.selection.SelectionOperator;

public class BinarySquareRunner {

    public static void main(String[] args) throws CloneNotSupportedException {
        int numberOfIndividuals = 4;
        int sizeOfIndividuals = 5;
        int maxIterations = 100;
        double mutationProbability = 0.5;
        int maxIterationsWithoutImprovements = 10;

        FitnessFunction<BinaryIndividual> fitnessFunction = new BinarySquareFunction();
        Initializer<BinaryIndividual> initializer = new FixedSizeBinaryRandomInitializer(numberOfIndividuals, sizeOfIndividuals);
        SelectionOperator<BinaryIndividual> selectionOperator = new RouletteWheelSelection<>();
        CrossoverOperator<BinaryIndividual> crossoverOperator = new SinglePointCrossover();
        MutationOperator<BinaryIndividual> mutationOperator = new SinglePointMutation();

        GeneticAlgorithm<BinaryIndividual> geneticAlgorithm = new SimpleGeneticAlgorithm<>(fitnessFunction, initializer,
                selectionOperator, crossoverOperator, mutationOperator, mutationProbability, maxIterations, maxIterationsWithoutImprovements);
        BinaryIndividual bestIndividual = geneticAlgorithm.run();

        System.out.println("Best individual: " + bestIndividual.getCoding() + " with fitness " + bestIndividual.getFitness());
    }
}
