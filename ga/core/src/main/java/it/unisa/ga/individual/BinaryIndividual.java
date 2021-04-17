package it.unisa.ga.individual;

public class BinaryIndividual extends EncodedIndividual<String> implements Decodable<Integer> {
    public BinaryIndividual(String encoding) {
        super(encoding);
    }

    @Override
    public Integer decode() {
        return Integer.parseUnsignedInt(getEncoding(), 2);
    }
}
