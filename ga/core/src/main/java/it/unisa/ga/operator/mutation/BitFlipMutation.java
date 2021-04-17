package it.unisa.ga.operator.mutation;

import it.unisa.ga.individual.EncodedIndividual;

import java.util.Random;

public class BitFlipMutation<T extends EncodedIndividual<String>> extends MutationOperator<T> {
    public BitFlipMutation(double mutationProbability, Random random) {
        super(mutationProbability, random);
    }

    @Override
    protected T mutate(T individual) throws CloneNotSupportedException {
        StringBuilder builder = new StringBuilder(individual.getEncoding());
        int i = getRandom().nextInt(builder.length());
        if (builder.charAt(i) == '0') {
            builder.setCharAt(i, '1');
        } else {
            builder.setCharAt(i, '0');
        }
        T mutatedIndividual = (T) individual.clone();
        mutatedIndividual.setEncoding(builder.toString());
        return mutatedIndividual;
    }
}
