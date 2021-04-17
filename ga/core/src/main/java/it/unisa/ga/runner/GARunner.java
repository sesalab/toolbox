package it.unisa.ga.runner;

import java.util.Map;

public abstract class GARunner {
    public abstract void run(Map<String, Double> input) throws CloneNotSupportedException;
}
