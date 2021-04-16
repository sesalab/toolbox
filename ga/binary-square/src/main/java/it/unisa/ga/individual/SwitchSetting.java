package it.unisa.ga.individual;

// TODO It should have the login of the 5 bit limit, not managed by other classes
public class SwitchSetting extends EncodedIndividual<String> implements Decodable<Integer> {
    public SwitchSetting(String coding) {
        super(coding);
    }

    @Override
    public Integer decode() {
        return Integer.parseUnsignedInt(getCoding(), 2);
    }

}
