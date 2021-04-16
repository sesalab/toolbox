package it.unisa.ga.fitness;

import it.unisa.ga.individual.ChessboardIndividual;

public class ConflictsFunction extends FitnessFunction<ChessboardIndividual> {

    public ConflictsFunction() {
        super(false);
    }

    @Override
    public void evaluate(ChessboardIndividual individual) {
        int conflicts = getConflicts(individual.getCoding());
        individual.setFitness(conflicts);
    }

    // A pair of queens in conflict is counted only once
    private int getConflicts(int[] decode) {
        int rowConflicts = 0;
        int upperRightConflicts = 0;
        int lowerRightConflicts = 0;
        for (int fixCol = 0; fixCol < decode.length; fixCol++) {
            int fixRow = decode[fixCol];
            for (int rightCol = fixCol + 1; rightCol < decode.length; rightCol++) {
                int rightRow = decode[rightCol];
                if (rightRow == fixRow) {
                    rowConflicts++;
                }
                if (rightRow == fixRow - (rightCol - fixCol)) {
                    upperRightConflicts++;
                }
                if (rightRow == fixRow + (rightCol - fixCol)) {
                    lowerRightConflicts++;
                }
            }
        }
        //System.out.println("Indiv:" + Arrays.toString(decode) + " " + rowConflicts + " " + upperRightConflicts + " " + lowerRightConflicts);
        return upperRightConflicts + lowerRightConflicts + rowConflicts;
    }
}
