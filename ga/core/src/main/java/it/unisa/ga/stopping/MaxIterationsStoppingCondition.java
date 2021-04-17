package it.unisa.ga.stopping;

import it.unisa.ga.metaheuristic.GenerationalGA;

public class MaxIterationsStoppingCondition extends StoppingCondition {
    private final int maxIterations;

    public MaxIterationsStoppingCondition(int maxIterations) {
        this.maxIterations = Math.max(maxIterations, 1);
    }

    @Override
    public boolean checkStop(GenerationalGA<?> ga) {
        return ga.getNumberOfGenerations() >= maxIterations;
    }

    public int getMaxIterations() {
        return maxIterations;
    }
}
