package org.computemetrics.core;

public abstract class Input {
    private final String[] metrics;

    public Input(String[] metrics) {
        this.metrics = metrics;
    }

    public String[] getMetrics() {
        return metrics;
    }
}
