package it.unisa.ga.operators.crossover;

import it.unisa.ga.individuals.BinaryIndividual;
import it.unisa.ga.populations.Population;

import java.util.List;
import java.util.Random;

public class SinglePointCrossover extends CrossoverOperator<BinaryIndividual> {

    @Override
    public Population<BinaryIndividual> apply(Population<BinaryIndividual> population, Random rand) {
        Population<BinaryIndividual> offsprings = new Population<>(population.getId() + 1);

        // Cross parents
        List<Pairing> pairings = makePairings(population);
        for (Pairing pairing : pairings) {
            String firstCoding = pairing.firstParent.getCoding();
            String secondCoding = pairing.secondParent.getCoding();
            int minLength = Math.min(firstCoding.length(), secondCoding.length());
            // e.g. if cutPoint = 1, then the cut occurs between 0 and 1, if cutpoint = 2, it occurs between 1 and 2, and so on...
            int cutPoint = rand.nextInt(minLength - 1) + 1;
            String firstCodingLeft = firstCoding.substring(0, cutPoint);
            String firstCodingRight = firstCoding.substring(cutPoint);
            String secondCodingLeft = secondCoding.substring(0, cutPoint);
            String secondCodingRight = secondCoding.substring(cutPoint);

            BinaryIndividual offspring1 = new BinaryIndividual(firstCodingLeft + secondCodingRight);
            BinaryIndividual offspring2 = new BinaryIndividual(secondCodingLeft + firstCodingRight);
            offsprings.add(offspring1);
            offsprings.add(offspring2);
        }
        return offsprings;
    }
}
