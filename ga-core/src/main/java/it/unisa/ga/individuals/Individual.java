package it.unisa.ga.individuals;

public abstract class Individual implements Comparable<Individual>, Cloneable {

    private double fitness;

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    // Comparing two individuals means comparing their fitness scores
    @Override
    public int compareTo(Individual other) {
        return Double.compare(this.fitness, other.fitness);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
