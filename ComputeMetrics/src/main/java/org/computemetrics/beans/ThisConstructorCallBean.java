package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.ConstructorInvocation;

import java.util.List;
import java.util.Objects;

public class ThisConstructorCallBean extends NodeBean<ConstructorInvocation> {
    private List<String> arguments;

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
        ThisConstructorCallBean that = (ThisConstructorCallBean) o;
        return Objects.equals(arguments, that.arguments);
    }

    @Override
    public String toString() {
        String argumentString = arguments.toString().replace("[", "").replace("]", "");
        return String.format("this(%s))", argumentString);
    }
}
