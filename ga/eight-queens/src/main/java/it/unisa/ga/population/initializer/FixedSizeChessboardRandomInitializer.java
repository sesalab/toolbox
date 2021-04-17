package it.unisa.ga.population.initializer;

import it.unisa.ga.individual.ChessboardIndividual;
import it.unisa.ga.population.UpperBoundedPopulation;
import it.unisa.ga.population.Population;

import java.util.Random;

public class FixedSizeChessboardRandomInitializer extends PopulationInitializer<ChessboardIndividual> {

    private final int numberOfIndividuals;
    private final int chessSize;

    public FixedSizeChessboardRandomInitializer(int numberOfIndividuals, int chessSize) {
        this.numberOfIndividuals = Math.max(numberOfIndividuals, 1);
        this.chessSize = Math.max(chessSize, 1);
    }

    @Override
    public Population<ChessboardIndividual> initialize() {
        UpperBoundedPopulation<ChessboardIndividual> population = new UpperBoundedPopulation<>(0, numberOfIndividuals);
        for (int i = 0; i < numberOfIndividuals; i++) {
            int[] randomCoding = generateRandomIntArray();
            ChessboardIndividual individual = new ChessboardIndividual(chessSize, chessSize, randomCoding);
            population.add(individual);
        }
        return population;
    }

    private int[] generateRandomIntArray() {
        Random rand = new Random();
        int[] randomCoding = new int[chessSize];
        for (int i = 0; i < chessSize; i++) {
            randomCoding[i] = rand.nextInt(chessSize);
        }
        return randomCoding;
    }

}
