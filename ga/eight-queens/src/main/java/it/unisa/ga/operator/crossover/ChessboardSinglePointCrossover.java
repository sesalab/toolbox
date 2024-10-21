package it.unisa.ga.operator.crossover;

import it.unisa.ga.individual.ChessboardIndividual;
import it.unisa.ga.population.Population;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ChessboardSinglePointCrossover<T extends ChessboardIndividual> extends CrossoverOperator<ChessboardIndividual> {

    public ChessboardSinglePointCrossover(double crossoverProbability, Random random) {
        super(crossoverProbability, random);
    }

    @Override
    public Population<ChessboardIndividual> apply(Population<ChessboardIndividual> population) throws CloneNotSupportedException {
        Population<ChessboardIndividual> offsprings = population.clone();
        offsprings.setId(population.getId() + 1);
        offsprings.clear();
        // Cross parents
        List<Pairing> pairings = makeRandomPairings(population);
        for (Pairing pairing : pairings) {
            Pairing children = cross(pairing);
            offsprings.add(children.firstIndividual);
            offsprings.add(children.secondIndividual);
        }
        return offsprings;
    }

    @Override
    protected CrossoverOperator<ChessboardIndividual>.Pairing cross(CrossoverOperator<ChessboardIndividual>.Pairing pairing) throws CloneNotSupportedException {

        int[] firstCoding = pairing.firstIndividual.getEncoding();
        int[] secondCoding = pairing.secondIndividual.getEncoding();
        int minLength = Math.min(firstCoding.length, secondCoding.length);

        // e.g. if cutPoint = 1, then the cut occurs between 0 and 1, if cutpoint = 2, it occurs between 1 and 2, and so on...
        int cutPoint = getRandom().nextInt(minLength - 1) + 1;
        int[] firstCodingLeft = Arrays.copyOfRange(firstCoding, 0, cutPoint);
        int[] firstCodingRight = Arrays.copyOfRange(firstCoding, cutPoint, minLength);
        int[] secondCodingLeft = Arrays.copyOfRange(secondCoding, 0, cutPoint);
        int[] secondCodingRight = Arrays.copyOfRange(secondCoding, cutPoint, minLength);

        ChessboardIndividual offspring1 = (ChessboardIndividual) pairing.firstIndividual.clone();
        offspring1.setEncoding(Stream.of(firstCodingLeft, secondCodingRight)
                .flatMapToInt(Arrays::stream)
                .toArray()
        );
        ChessboardIndividual offspring2 = (ChessboardIndividual) pairing.secondIndividual.clone();
        offspring2.setEncoding(Stream.of(secondCodingLeft, firstCodingRight)
                .flatMapToInt(Arrays::stream)
                .toArray()
        );
        return new Pairing(offspring1, offspring2);
    }
}
