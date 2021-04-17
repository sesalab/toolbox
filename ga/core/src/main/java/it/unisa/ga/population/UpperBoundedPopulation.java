package it.unisa.ga.population;

import it.unisa.ga.individual.Individual;

public class UpperBoundedPopulation<T extends Individual> extends Population<T> {
    private final int upperBound;

    public UpperBoundedPopulation(long id, int upperBound) {
        super(id);
        this.upperBound = Math.max(upperBound, 1);
    }

    @Override
    public boolean add(T individual) {
        if (0 < upperBound && upperBound <= size()) {
            return false;
        }
        super.add(individual);
        return true;
    }

    public int getUpperBound() {
        return upperBound;
    }

}
