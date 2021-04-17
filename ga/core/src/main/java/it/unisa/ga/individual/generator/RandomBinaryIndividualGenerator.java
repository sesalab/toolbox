package it.unisa.ga.individual.generator;

import it.unisa.ga.individual.BinaryIndividual;

public class RandomBinaryIndividualGenerator extends IndividualGenerator<BinaryIndividual> {
    private final int sizeOfIndividuals;

    public RandomBinaryIndividualGenerator(int sizeOfIndividuals) {
        this.sizeOfIndividuals = sizeOfIndividuals;
    }

    public BinaryIndividual generateIndividual() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < sizeOfIndividuals; i++) {
            if (Math.random() < 0.5) {
                randomString.append("0");
            } else {
                randomString.append("1");
            }
        }
        return new BinaryIndividual(randomString.toString());
    }
}
