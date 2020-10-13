package it.unisa.ga.individuals;

public class IntegerStringIndividual extends StringIndividual<Integer> {

    public IntegerStringIndividual(String coding) {
        super(coding);
    }

    @Override
    public Integer decode() {
        return Integer.parseUnsignedInt(getCoding(), 2);
    }

}
