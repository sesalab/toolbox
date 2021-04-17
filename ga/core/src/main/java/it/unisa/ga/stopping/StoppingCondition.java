package it.unisa.ga.stopping;

import it.unisa.ga.metaheuristic.GenerationalGA;

public abstract class StoppingCondition {
    public abstract boolean checkStop(GenerationalGA<?> ga);
}
