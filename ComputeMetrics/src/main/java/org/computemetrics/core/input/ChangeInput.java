package org.computemetrics.core.input;

import org.computemetrics.core.Input;

public class ChangeInput extends Input {
    private final String beforeFile;
    private final String afterFile;

    public ChangeInput(String beforeFile, String afterFile, String[] metrics) {
        super(metrics);
        this.beforeFile = beforeFile;
        this.afterFile = afterFile;
    }
    public String getBeforeFile() {
        return beforeFile;
    }

    public String getAfterFile() {
        return afterFile;
    }
}
