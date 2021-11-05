package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.ForStatement;

import java.util.List;
import java.util.Objects;

public class ForBean extends NodeBean<ForStatement> {
    private List<String> inizializers;
    private String expression;
    private List<String> updaters;
    private String body;

    public List<String> getInizializers() {
        return inizializers;
    }

    public void setInizializers(List<String> inizializers) {
        this.inizializers = inizializers;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<String> getUpdaters() {
        return updaters;
    }

    public void setUpdaters(List<String> updaters) {
        this.updaters = updaters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isInfiniteLoop() {
        return expression == null || expression.equals("true");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForBean forBean = (ForBean) o;
        return Objects.equals(expression, forBean.expression);
    }

    @Override
    public String toString() {
        String initString = inizializers.toString().replace("[", "").replace("]", "");
        String updatString = inizializers.toString().replace("[", "").replace("]", "");
        return String.format("for (%s; %s; %s)", initString, expression, updatString);
    }
}
