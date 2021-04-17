package it.unisa.ga.individual;

public class Switches extends EncodedIndividual<String> implements Decodable<Integer> {
    public Switches(String coding) {
        super(coding);
    }

    @Override
    public Integer decode() {
        return Integer.parseUnsignedInt(getEncoding(), 2);
    }

}
