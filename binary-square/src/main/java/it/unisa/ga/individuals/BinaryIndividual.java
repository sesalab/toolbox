package it.unisa.ga.individuals;

public class BinaryIndividual extends StringIndividual<Integer> {

    public BinaryIndividual(String coding) {
        super(coding);
    }

    @Override
    public Integer decode() {
        return Integer.parseUnsignedInt(getCoding(), 2);
    }

}
