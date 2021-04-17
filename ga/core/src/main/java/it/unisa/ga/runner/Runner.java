package it.unisa.ga.runner;

import java.util.Map;

public abstract class Runner {
    public abstract void run(Map<String, Double> input) throws CloneNotSupportedException;
}
