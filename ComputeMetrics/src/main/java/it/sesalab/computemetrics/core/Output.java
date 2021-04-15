package it.sesalab.computemetrics.core;

import java.util.Map;

public class Output {
    private final String directory;
    private final String file;
    private final Map<String, Double> metrics;

    public Output(String directory, String file, Map<String, Double> metrics) {
        this.directory = directory;
        this.file = file;
        this.metrics = metrics;
    }

    public String getDirectory() {
        return directory;
    }

    public String getFile() {
        return file;
    }

    public Map<String, Double> getMetrics() {
        return metrics;
    }
}
