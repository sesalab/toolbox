package it.unisa.ga.population.initializer;

import it.unisa.ga.individual.Switches;
import it.unisa.ga.population.UpperBoundedPopulation;
import it.unisa.ga.population.Population;

public class RandomUpperBoundedPopulationInitializer extends PopulationInitializer<Switches> {
    private final int numberOfIndividuals;
    private final RandomSwitchSettingGenerator randomSwitchSettingGenerator;

    // The given integer will be set as the maximum size of the initialized population
    public RandomUpperBoundedPopulationInitializer(int numberOfIndividuals, int sizeOfIndividuals) {
        this.numberOfIndividuals = Math.max(numberOfIndividuals, 1);
        this.randomSwitchSettingGenerator = new RandomSwitchSettingGenerator(Math.max(sizeOfIndividuals, 1));
    }

    @Override
    public Population<Switches> initialize() {
        UpperBoundedPopulation<Switches> population = new UpperBoundedPopulation<>(0, numberOfIndividuals);
        for (int i = 0; i < numberOfIndividuals; i++) {
            Switches individual = randomSwitchSettingGenerator.generateIndividual();
            population.add(individual);
        }
        return population;
    }
}
