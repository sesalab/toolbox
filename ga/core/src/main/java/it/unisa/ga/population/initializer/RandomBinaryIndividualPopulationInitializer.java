package it.unisa.ga.population.initializer;

import it.unisa.ga.individual.BinaryIndividual;
import it.unisa.ga.individual.generator.RandomBinaryIndividualGenerator;
import it.unisa.ga.population.Population;
import it.unisa.ga.population.UpperBoundedPopulation;

public class RandomBinaryIndividualPopulationInitializer extends PopulationInitializer<BinaryIndividual> {
    private final int numberOfIndividuals;
    private final RandomBinaryIndividualGenerator randomBinaryIndividualGenerator;

    // The given integer will be set as the maximum size of the initialized population
    public RandomBinaryIndividualPopulationInitializer(int numberOfIndividuals, int sizeOfIndividuals) {
        this.numberOfIndividuals = Math.max(numberOfIndividuals, 1);
        this.randomBinaryIndividualGenerator = new RandomBinaryIndividualGenerator(Math.max(sizeOfIndividuals, 1));
    }

    @Override
    public Population<BinaryIndividual> initialize() {
        UpperBoundedPopulation<BinaryIndividual> population = new UpperBoundedPopulation<>(0, numberOfIndividuals);
        for (int i = 0; i < numberOfIndividuals; i++) {
            BinaryIndividual individual = randomBinaryIndividualGenerator.generateIndividual();
            population.add(individual);
        }
        return population;
    }
}
