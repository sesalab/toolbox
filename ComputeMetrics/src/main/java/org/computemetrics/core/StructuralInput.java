package org.computemetrics.core;

public class StructuralInput extends Input {
    private final String directory;
    private final String file;

    public StructuralInput(String directory, String file, String[] metrics) {
        super(metrics);
        this.directory = directory;
        this.file = file;
    }

    public String getDirectory() {
        return directory;
    }

    public String getFile() {
        return file;
    }
}
