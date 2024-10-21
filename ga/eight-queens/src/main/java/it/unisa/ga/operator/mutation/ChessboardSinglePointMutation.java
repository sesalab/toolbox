package it.unisa.ga.operator.mutation;

import it.unisa.ga.individual.ChessboardIndividual;
import it.unisa.ga.individual.Individual;

import java.util.Random;

public class ChessboardSinglePointMutation<T extends it.unisa.ga.individual.ChessboardIndividual> extends MutationOperator<ChessboardIndividual>{

    public ChessboardSinglePointMutation(double mutationProbability, Random random) {
        super(mutationProbability, random);
    }

    @Override
    protected ChessboardIndividual mutate(ChessboardIndividual individual) throws CloneNotSupportedException {
        Random rand = new Random();
        int[] coding = individual.getEncoding();
        int col = rand.nextInt(coding.length);
        int row = rand.nextInt(individual.getMaxRows());
        coding[col] = row;
        ChessboardIndividual mutatedIndividual = (ChessboardIndividual) individual.clone();
        mutatedIndividual.setEncoding(coding);
        return mutatedIndividual;
    }
}
