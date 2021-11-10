package org.computemetrics.core.input;

import org.computemetrics.core.Input;

import java.util.List;

public class FilesInput extends Input {
    private final String directory;
    private final List<String> files;

    public FilesInput(String directory, List<String> files, String[] metrics) {
        super(metrics);
        this.directory = directory;
        this.files = files;
    }

    public String getDirectory() {
        return directory;
    }

    public List<String> getFiles() {
        return files;
    }
}
