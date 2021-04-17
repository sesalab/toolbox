package it.unisa.ga.operator.mutation;

import it.unisa.ga.individual.Switches;

import java.util.Random;

public class SwitchSettingSinglePointMutation extends RandomResettingMutation<Switches> {
    @Override
    protected Switches mutate(Switches individual, Random rand) throws CloneNotSupportedException {
        StringBuilder builder = new StringBuilder(individual.getCoding());
        int i = rand.nextInt(builder.length());
        if (builder.charAt(i) == '0') {
            builder.setCharAt(i, '1');
        } else {
            builder.setCharAt(i, '0');
        }
        Switches mutatedIndividual = (Switches) individual.clone();
        mutatedIndividual.setCoding(builder.toString());
        return mutatedIndividual;
    }
}
