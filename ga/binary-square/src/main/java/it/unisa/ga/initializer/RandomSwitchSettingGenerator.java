package it.unisa.ga.initializer;

import it.unisa.ga.individual.SwitchSetting;

public class RandomSwitchSettingGenerator extends IndividualGenerator<SwitchSetting> {
    private final int sizeOfIndividuals;

    public RandomSwitchSettingGenerator(int sizeOfIndividuals) {
        this.sizeOfIndividuals = sizeOfIndividuals;
    }

    public SwitchSetting generateIndividual() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < sizeOfIndividuals; i++) {
            if (Math.random() < 0.5) {
                randomString.append("0");
            } else {
                randomString.append("1");
            }
        }
        return new SwitchSetting(randomString.toString());
    }
}
