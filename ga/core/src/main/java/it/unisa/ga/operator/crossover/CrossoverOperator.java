package it.unisa.ga.operator.crossover;

import it.unisa.ga.individual.Individual;
import it.unisa.ga.operator.GeneticOperator;
import it.unisa.ga.population.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class CrossoverOperator<T extends Individual> extends GeneticOperator<T> {
    private final double crossoverProbability;

    public CrossoverOperator(double crossoverProbability, Random random) {
        super(random);
        if (0.0 <= crossoverProbability && crossoverProbability <= 1.0) {
            this.crossoverProbability = crossoverProbability;
        } else {
            this.crossoverProbability = 0.1;
        }
    }

    class Pairing {
        protected final T firstIndividual;
        protected final T secondIndividual;

        protected Pairing(T firstIndividual, T secondIndividual) {
            this.firstIndividual = firstIndividual;
            this.secondIndividual = secondIndividual;
        }
    }

    public Population<T> apply(Population<T> population) throws CloneNotSupportedException {
        Population<T> offsprings = population.clone();
        offsprings.setId(population.getId() + 1);
        offsprings.clear();

        // Pair and cross parents
        List<Pairing> pairings = makeRandomPairings(population);
        for (Pairing pairing : pairings) {
            if (getRandom().nextDouble() < getCrossoverProbability()) {
                Pairing children = cross(pairing);
                offsprings.add(children.firstIndividual);
                offsprings.add(children.secondIndividual);
            } else {
                offsprings.add((T) pairing.firstIndividual.clone());
                offsprings.add((T) pairing.secondIndividual.clone());
            }
        }
        return offsprings;
    }

    protected abstract Pairing cross(Pairing pairing) throws CloneNotSupportedException;

    protected List<Pairing> makeRandomPairings(Population<T> population) {
        List<Pairing> pairings = new ArrayList<>();
        ArrayList<T> populationList = new ArrayList<>(population);
        // If there is only one individual, pair with itself!
        if (population.size() < 2) {
            T loneIndividual = populationList.get(0);
            Pairing pairing = new Pairing(loneIndividual, loneIndividual);
            pairings.add(pairing);
        } else {
            // Convert to list and randomly permute it
            Collections.shuffle(populationList);
            // If odd, the last element is not considered
            if (populationList.size() % 2 != 0) {
                populationList.remove(populationList.size() - 1);
            }
            for (int i = 0; i < populationList.size(); i = i + 2) {
                T firstParent = populationList.get(i);
                T secondParent = populationList.get(i + 1);
                Pairing pairing = new Pairing(firstParent, secondParent);
                pairings.add(pairing);
            }
        }
        return pairings;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }
}
