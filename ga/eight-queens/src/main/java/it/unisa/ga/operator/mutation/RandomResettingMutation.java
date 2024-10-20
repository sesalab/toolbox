package it.unisa.ga.operator.mutation;

import it.unisa.ga.individual.ChessboardIndividual;

import java.util.Random;

public abstract class RandomResettingMutation<ChessboardIndividual> {
    protected abstract ChessboardIndividual mutate(it.unisa.ga.individual.ChessboardIndividual individual, Random rand) throws CloneNotSupportedException;
}
