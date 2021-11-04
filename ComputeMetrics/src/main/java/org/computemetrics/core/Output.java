package org.computemetrics.core;

import java.util.Map;

public class Output {
    private final Map<String, String> attributes;
    private final Map<String, Double> metrics;

    public Output(Map<String, String> attributes, Map<String, Double> metrics) {
        this.attributes = attributes;
        this.metrics = metrics;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public Map<String, Double> getMetrics() {
        return metrics;
    }
}
