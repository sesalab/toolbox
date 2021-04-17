package it.unisa.ga.operator.selection;

import it.unisa.ga.individual.Individual;
import it.unisa.ga.operator.GeneticOperator;

import java.util.Random;

public abstract class SelectionOperator<T extends Individual> extends GeneticOperator<T> {
    public SelectionOperator(Random random) {
        super(random);
    }
}
