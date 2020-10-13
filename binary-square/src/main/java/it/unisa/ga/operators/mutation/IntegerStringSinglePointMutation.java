package it.unisa.ga.operators.mutation;

import it.unisa.ga.individuals.IntegerStringIndividual;

import java.util.Random;

public class IntegerStringSinglePointMutation extends SinglePointMutation<IntegerStringIndividual> {

    @Override
    protected IntegerStringIndividual mutate(IntegerStringIndividual individual, Random rand) throws CloneNotSupportedException {
        StringBuilder builder = new StringBuilder(individual.getCoding());
        int i = rand.nextInt(builder.length());
        if (builder.charAt(i) == '0') {
            builder.setCharAt(i, '1');
        } else {
            builder.setCharAt(i, '0');
        }
        IntegerStringIndividual mutatedIndividual = (IntegerStringIndividual) individual.clone();
        mutatedIndividual.setCoding(builder.toString());
        return mutatedIndividual;
    }
}
