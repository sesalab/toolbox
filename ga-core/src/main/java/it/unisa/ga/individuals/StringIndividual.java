package it.unisa.ga.individuals;

public abstract class StringIndividual<T> extends Individual {

    private final String coding;

    public StringIndividual(String coding) {
        this.coding = coding;
    }

    public String getCoding() {
        return coding;
    }

    public abstract T decode();

}
