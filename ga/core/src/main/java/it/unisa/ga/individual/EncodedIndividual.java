package it.unisa.ga.individual;

public abstract class EncodedIndividual<T> extends Individual {
    protected T encoding;

    public EncodedIndividual(T encoding) {
        this.encoding = encoding;
    }

    public T getEncoding() {
        return encoding;
    }

    public void setEncoding(T encoding) {
        this.encoding = encoding;
    }

    @Override
    public String toString() {
        return "EncodedIndividual{" +
                "coding=" + encoding +
                ", fitness=" + fitness +
                '}';
    }
}
