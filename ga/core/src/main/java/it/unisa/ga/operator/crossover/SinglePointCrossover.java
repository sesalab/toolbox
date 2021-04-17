package it.unisa.ga.operator.crossover;

import it.unisa.ga.individual.EncodedIndividual;

import java.util.Random;

public class SinglePointCrossover<T extends EncodedIndividual<String>> extends CrossoverOperator<T> {
    public SinglePointCrossover(double crossoverProbability, Random random) {
        super(crossoverProbability, random);
    }

    @Override
    protected Pairing cross(Pairing pairing) throws CloneNotSupportedException {
        String firstCoding = pairing.firstIndividual.getEncoding();
        String secondCoding = pairing.secondIndividual.getEncoding();
        int minLength = Math.min(firstCoding.length(), secondCoding.length());
        // e.g. if cutPoint = 1, then the cut occurs between 0 and 1, if cutpoint = 2, it occurs between 1 and 2, and so on...
        int cutPoint = getRandom().nextInt(minLength - 1) + 1;
        String firstCodingLeft = firstCoding.substring(0, cutPoint);
        String firstCodingRight = firstCoding.substring(cutPoint);
        String secondCodingLeft = secondCoding.substring(0, cutPoint);
        String secondCodingRight = secondCoding.substring(cutPoint);

        T offspring1 = (T) pairing.firstIndividual.clone();
        offspring1.setEncoding(firstCodingLeft + secondCodingRight);
        T offspring2 = (T) pairing.secondIndividual.clone();
        offspring2.setEncoding(secondCodingLeft + firstCodingRight);
        return new Pairing(offspring1, offspring2);
    }
}
