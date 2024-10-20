package it.unisa.ga.operator.mutation;

import it.unisa.ga.individual.ChessboardIndividual;
import it.unisa.ga.individual.Individual;

import java.util.Random;

public abstract class RandomResettingMutation<ChessboardIndividual extends Individual> extends MutationOperator<Individual>{
    public RandomResettingMutation(double mutationProbability, Random random) {
        super(mutationProbability, random);
    }
}
