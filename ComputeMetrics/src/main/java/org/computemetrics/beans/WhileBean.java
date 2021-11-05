package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.WhileStatement;

import java.util.Objects;

public class WhileBean extends NodeBean<WhileStatement> {
    private String expression;
    private String body;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WhileBean whileBean = (WhileBean) o;
        return Objects.equals(expression, whileBean.expression);
    }

    @Override
    public String toString() {
        return String.format("while (%s)", expression);
    }
}
