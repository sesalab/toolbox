package it.unisa.ga.operators.crossover;

import it.unisa.ga.individuals.IntegerStringIndividual;
import it.unisa.ga.populations.Population;

import java.util.List;
import java.util.Random;

public class IntegerStringSinglePointCrossover extends CrossoverOperator<IntegerStringIndividual> {

    @Override
    public Population<IntegerStringIndividual> apply(Population<IntegerStringIndividual> population, Random rand) throws CloneNotSupportedException {
        Population<IntegerStringIndividual> offsprings = population.clone();
        offsprings.setId(population.getId() + 1);
        offsprings.clear();

        // Cross parents
        List<Pairing> pairings = makeRandomPairings(population);
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

            IntegerStringIndividual offspring1 = (IntegerStringIndividual) pairing.firstParent.clone();
            offspring1.setCoding(firstCodingLeft + secondCodingRight);
            IntegerStringIndividual offspring2 = (IntegerStringIndividual) pairing.secondParent.clone();
            offspring2.setCoding(secondCodingLeft + firstCodingRight);
            offsprings.add(offspring1);
            offsprings.add(offspring2);
        }
        return offsprings;
    }
}
