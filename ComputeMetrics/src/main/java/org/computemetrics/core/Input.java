package org.computemetrics.core;

public class Input {
    private final String directory;
    private final String file;
    private final String[] metrics;

    public Input(String directory, String file, String[] metrics) {
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

    public String[] getMetrics() {
        return metrics;
    }
}
