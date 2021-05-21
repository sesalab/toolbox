package org.computemetrics.core;

import java.util.Map;

public class Output {
    private final String project;
    private final String clazz;
    private final String directory;
    private final String file;
    private final Map<String, Double> metrics;

    public Output(String project, String clazz, String directory, String file, Map<String, Double> metrics) {
        this.project = project;
        this.clazz = clazz;
        this.directory = directory;
        this.file = file;
        this.metrics = metrics;
    }

    public String getProject() {
        return project;
    }

    public String getClazz() {
        return clazz;
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
