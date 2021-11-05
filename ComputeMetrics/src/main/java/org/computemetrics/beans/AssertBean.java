package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.AssertStatement;

import java.util.Objects;

public class AssertBean extends NodeBean<AssertStatement> {
    private String expression;
    private String message;

    public AssertBean() {
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssertBean that = (AssertBean) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public String toString() {
        String first = String.format("assert %s", expression);
        if (message != null) {
            return first + String.format(": %s", message);
        } else {
            return first;
        }
    }
}
