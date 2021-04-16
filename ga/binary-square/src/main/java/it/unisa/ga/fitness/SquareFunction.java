package it.unisa.ga.fitness;

import it.unisa.ga.individual.SwitchSetting;

public class SquareFunction extends FitnessFunction<SwitchSetting> {
    public SquareFunction() {
        super(true);
    }

    public void evaluate(SwitchSetting individual) {
        int value = individual.decode();
        individual.setFitness(value * value);
    }
}
