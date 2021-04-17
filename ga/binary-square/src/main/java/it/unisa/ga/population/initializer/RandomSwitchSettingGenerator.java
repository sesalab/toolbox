package it.unisa.ga.population.initializer;

import it.unisa.ga.individual.Switches;

public class RandomSwitchSettingGenerator extends IndividualGenerator<Switches> {
    private final int sizeOfIndividuals;

    public RandomSwitchSettingGenerator(int sizeOfIndividuals) {
        this.sizeOfIndividuals = sizeOfIndividuals;
    }

    public Switches generateIndividual() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < sizeOfIndividuals; i++) {
            if (Math.random() < 0.5) {
                randomString.append("0");
            } else {
                randomString.append("1");
            }
        }
        return new Switches(randomString.toString());
    }
}
