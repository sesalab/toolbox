package it.unisa.ga.operator.mutation;

import it.unisa.ga.individual.ChessboardIndividual;

import java.util.Random;

public class ChessboardSinglePointMutation extends RandomResettingMutation<ChessboardIndividual> {
    
    @Override
    protected ChessboardIndividual mutate(ChessboardIndividual individual, Random rand) throws CloneNotSupportedException {
        int[] coding = individual.getCoding();
        int col = rand.nextInt(coding.length);
        int row = rand.nextInt(individual.getMaxRows());
        coding[col] = row;
        ChessboardIndividual mutatedIndividual = (ChessboardIndividual) individual.clone();
        mutatedIndividual.setCoding(coding);
        return mutatedIndividual;
    }
}
