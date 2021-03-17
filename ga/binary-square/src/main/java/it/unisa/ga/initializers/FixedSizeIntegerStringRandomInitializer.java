package it.unisa.ga.initializers;

import it.unisa.ga.individuals.IntegerStringIndividual;
import it.unisa.ga.populations.FixedSizePopulation;
import it.unisa.ga.populations.Population;

public class FixedSizeIntegerStringRandomInitializer extends Initializer<IntegerStringIndividual> {

    private final int numberOfIndividuals;
    private final int sizeOfIndividuals;

    // The given integer will be set as the maximum size of the initialized population
    public FixedSizeIntegerStringRandomInitializer(int numberOfIndividuals, int sizeOfIndividuals) {
        this.numberOfIndividuals = Math.max(numberOfIndividuals, 1);
        this.sizeOfIndividuals = Math.max(sizeOfIndividuals, 1);
    }

    @Override
    public Population<IntegerStringIndividual> initialize() {
        FixedSizePopulation<IntegerStringIndividual> population = new FixedSizePopulation<>(0, numberOfIndividuals);
        for (int i = 0; i < numberOfIndividuals; i++) {
            String randomCoding = generateRandomBinaryString();
            IntegerStringIndividual individual = new IntegerStringIndividual(randomCoding);
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
