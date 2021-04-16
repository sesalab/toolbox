package it.unisa.ga.individual;

import java.util.Arrays;

public class ChessboardIndividual extends IntArrayIndividual {

    private final int cols;
    private final int maxRows;

    public ChessboardIndividual(int cols, int maxRows, int[] coding) {
        super(coding);
        this.cols = cols;
        this.maxRows = maxRows;

        // Bring the length to cols, with possible truncation or padding
        int[] newCoding = Arrays.copyOf(coding, cols);

        // Negative values -> 0; Over maxRows values -> maxRows - 1
        for (int i = 0; i < newCoding.length; i++) {
            if (newCoding[i] < 0) {
                newCoding[i] = 0;
            } else if (newCoding[i] >= maxRows) {
                newCoding[i] = maxRows - 1;
            }
        }
        setCoding(newCoding);
    }

    public int getCols() {
        return cols;
    }

    public int getMaxRows() {
        return maxRows;
    }

    @Override
    public String toString() {
        return "ChessboardIndividual{" +
                super.toString() +
                "cols=" + cols +
                ", maxRows=" + maxRows +
                '}';
    }
}
