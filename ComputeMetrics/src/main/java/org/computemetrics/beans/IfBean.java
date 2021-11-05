package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.IfStatement;

import java.util.Objects;

public class IfBean extends NodeBean<IfStatement> {
    private String expression;
    private String thenStatement;
    private String elseStatement;

    public IfBean() {
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getThenStatement() {
        return thenStatement;
    }

    public void setThenStatement(String thenStatement) {
        this.thenStatement = thenStatement;
    }

    public String getElseStatement() {
        return elseStatement;
    }

    public void setElseStatement(String elseStatement) {
        this.elseStatement = elseStatement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IfBean ifBean = (IfBean) o;
        return Objects.equals(expression, ifBean.expression);
    }

    @Override
    public String toString() {
        return String.format("if (%s)", expression);
    }
}
