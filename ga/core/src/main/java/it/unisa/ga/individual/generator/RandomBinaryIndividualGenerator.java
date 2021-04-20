package it.unisa.ga.individual.generator;

import it.unisa.ga.individual.BinaryIndividual;

import java.util.Random;

public class RandomBinaryIndividualGenerator extends IndividualGenerator<BinaryIndividual> {
    private final int sizeOfIndividuals;
    private final Random random;

    public RandomBinaryIndividualGenerator(int sizeOfIndividuals, Random random) {
        this.sizeOfIndividuals = Math.max(sizeOfIndividuals, 1);
        this.random = random;
    }

    @Override
    public BinaryIndividual generateIndividual() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < sizeOfIndividuals; i++) {
            if (random.nextDouble() < 0.5) {
                randomString.append("0");
            } else {
                randomString.append("1");
            }
        }
        return new BinaryIndividual(randomString.toString());
    }
}
