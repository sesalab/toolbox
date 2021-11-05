package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.ConditionalExpression;

import java.util.Objects;

public class ConditionalBean extends NodeBean<ConditionalExpression> {
    private String expression;
    private String thenExpression;
    private String elseExpression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getThenExpression() {
        return thenExpression;
    }

    public void setThenExpression(String thenExpression) {
        this.thenExpression = thenExpression;
    }

    public String getElseExpression() {
        return elseExpression;
    }

    public void setElseExpression(String elseExpression) {
        this.elseExpression = elseExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConditionalBean that = (ConditionalBean) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public String toString() {
        return String.format("%s ? %s : %s", expression, thenExpression, elseExpression);
    }
}
