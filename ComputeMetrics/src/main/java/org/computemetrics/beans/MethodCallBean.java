package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.MethodInvocation;

import java.util.List;
import java.util.Objects;

public class MethodCallBean extends NodeBean<MethodInvocation> {
    private String caller;
    private String called;
    private List<String> arguments;

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodCallBean that = (MethodCallBean) o;
        return Objects.equals(called, that.called) && Objects.equals(arguments, that.arguments);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (caller != null) {
            builder.append(String.format("%s.", caller));
        }
        String argumentString = arguments.toString().replace("[", "").replace("]", "");
        builder.append(String.format("%s(%s))", called, argumentString));
        return builder.toString();
    }
}
