package it.unisa.ga.main;

import it.unisa.ga.runner.BinarySquareRunner;

import java.util.HashMap;
import java.util.Map;

public class BinarySquareMain {
    public static void main(String[] args) throws CloneNotSupportedException {
        Map<String, Double> input = new HashMap<>();
        input.put("numberOfIndividuals", 4.0);
        input.put("sizeOfIndividuals", 5.0);
        input.put("mutationProbability", 0.5);
        input.put("maxIterations", 100.0);
        // Set this to 0 if you want to always reach maxIterations
        input.put("maxIterationsNoImprovements", 10.0);
        new BinarySquareRunner().run(input);
    }
}
