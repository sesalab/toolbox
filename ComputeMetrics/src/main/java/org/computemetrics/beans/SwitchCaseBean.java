package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.SwitchCase;

import java.util.List;
import java.util.Objects;

public class SwitchCaseBean extends NodeBean<SwitchCase> {
    private List<String> expressions;

    public List<String> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<String> expressions) {
        this.expressions = expressions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwitchCaseBean that = (SwitchCaseBean) o;
        return Objects.equals(expressions, that.expressions);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String expression : expressions) {
            builder.append(String.format("case %s:\n", expression));
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }
}
