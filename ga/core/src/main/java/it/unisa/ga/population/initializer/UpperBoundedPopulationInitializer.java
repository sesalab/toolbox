package it.unisa.ga.population.initializer;

import it.unisa.ga.individual.Individual;
import it.unisa.ga.individual.generator.IndividualGenerator;
import it.unisa.ga.population.Population;
import it.unisa.ga.population.UpperBoundedPopulation;

public class UpperBoundedPopulationInitializer<T extends Individual> extends PopulationInitializer<T> {
    private final int numberOfIndividuals;
    private final IndividualGenerator<T> individualGenerator;

    // The given integer will be set as the maximum size of the initialized population
    public UpperBoundedPopulationInitializer(int numberOfIndividuals, IndividualGenerator<T> individualGenerator) {
        this.numberOfIndividuals = Math.max(numberOfIndividuals, 1);
        this.individualGenerator = individualGenerator;
    }

    @Override
    public Population<T> initialize() {
        Population<T> population = new UpperBoundedPopulation<>(0, numberOfIndividuals);
        for (int i = 0; i < numberOfIndividuals; i++) {
            T individual = individualGenerator.generateIndividual();
            population.add(individual);
        }
        return population;
    }
}
