package it.unisa.ga.individuals;

public class BinaryIndividual extends Individual {

    private final String coding;

    public BinaryIndividual(String coding) {
        this.coding = coding;
    }

    public String getCoding() {
        return coding;
    }

    public int decode() {
        return Integer.parseUnsignedInt(coding, 2);
    }

}
