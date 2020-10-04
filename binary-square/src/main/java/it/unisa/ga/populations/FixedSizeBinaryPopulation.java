package it.unisa.ga.populations;

import it.unisa.ga.individuals.BinaryIndividual;

public class FixedSizeBinaryPopulation extends Population<BinaryIndividual> {

    private final int maxSize;

    public FixedSizeBinaryPopulation(int maxSize) {
        this.maxSize = Math.max(maxSize, 0);
    }

    @Override
    public boolean add(BinaryIndividual individual) {
        if (super.size() >= maxSize) {
            return false;
        }
        super.add(individual);
        return true;
    }

}
