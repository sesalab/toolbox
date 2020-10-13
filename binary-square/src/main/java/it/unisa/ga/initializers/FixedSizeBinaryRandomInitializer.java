package it.unisa.ga.initializers;

import it.unisa.ga.individuals.BinaryIndividual;
import it.unisa.ga.populations.FixedSizeBinaryPopulation;
import it.unisa.ga.populations.Population;

public class FixedSizeBinaryRandomInitializer extends Initializer<BinaryIndividual> {

    private final int numberOfIndividuals;
    private final int sizeOfIndividuals;

    // The given integer will be set as the maximum size of the initialized population
    public FixedSizeBinaryRandomInitializer(int numberOfIndividuals, int sizeOfIndividuals) {
        this.numberOfIndividuals = Math.max(numberOfIndividuals, 1);
        this.sizeOfIndividuals = Math.max(sizeOfIndividuals, 1);
    }

    @Override
    public Population<BinaryIndividual> initialize() {
        FixedSizeBinaryPopulation population = new FixedSizeBinaryPopulation(0, numberOfIndividuals);
        for (int i = 0; i < numberOfIndividuals; i++) {
            String randomCoding = generateRandomBinaryString();
            BinaryIndividual individual = new BinaryIndividual(randomCoding);
            population.add(individual);
        }
        return population;
    }

    private String generateRandomBinaryString() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < sizeOfIndividuals; i++) {
            if (Math.random() < 0.5) {
                randomString.append("0");
            } else {
                randomString.append("1");
            }
        }
        return randomString.toString();
    }
}
