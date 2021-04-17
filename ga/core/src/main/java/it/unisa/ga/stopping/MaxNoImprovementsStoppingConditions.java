package it.unisa.ga.stopping;

import it.unisa.ga.metaheuristic.GenerationalGA;
import it.unisa.ga.population.Population;

import java.util.List;

public class MaxNoImprovementsStoppingConditions extends StoppingCondition {
    private final int tolerance;

    public MaxNoImprovementsStoppingConditions(int tolerance) {
        this.tolerance = Math.max(tolerance, 1);
    }

    @Override
    public boolean checkStop(GenerationalGA<?> ga) {
        // Do not apply this check if the number of generations is insufficient
        if (ga.getNumberOfGenerations() <= tolerance) {
            return false;
        }
        List<? extends Population<?>> windowGenerations = ga.getLastGenerations(tolerance);
        Population<?> bestWindowGeneration = windowGenerations.stream().max(Population::compareTo).orElse(null);

        // The last generation BEFORE the tolerance window
        Population<?> referenceGeneration = ga.getLastButGeneration(tolerance);

        // Return true is the best generation in the tolerance window is not better than the
        return bestWindowGeneration.compareTo(referenceGeneration) <= 0;
    }

    public int getTolerance() {
        return tolerance;
    }
}
