package it.unisa.ga.operators.mutation;

import it.unisa.ga.individuals.BinaryIndividual;
import it.unisa.ga.populations.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SinglePointMutation extends MutationOperator<BinaryIndividual> {

    @Override
    public Population<BinaryIndividual> apply(Population<BinaryIndividual> population, Random rand) {
        List<BinaryIndividual> populationList = new ArrayList<>(population);
        BinaryIndividual chosenIndividual = populationList.get(rand.nextInt(populationList.size()));
        BinaryIndividual mutatedIndividual = flipRandomBit(chosenIndividual, rand);
        populationList.remove(chosenIndividual);
        populationList.add(mutatedIndividual);

        Population<BinaryIndividual> newPopulation = new Population<>(population.getId() + 1);
        newPopulation.addAll(populationList);
        return newPopulation;
    }

    protected BinaryIndividual flipRandomBit(BinaryIndividual individual, Random rand) {
        StringBuilder builder = new StringBuilder(individual.getCoding());
        int i = rand.nextInt(builder.length());
        if (builder.charAt(i) == '0') {
            builder.setCharAt(i, '1');
        } else {
            builder.setCharAt(i, '0');
        }
        return new BinaryIndividual(builder.toString());
    }
}
