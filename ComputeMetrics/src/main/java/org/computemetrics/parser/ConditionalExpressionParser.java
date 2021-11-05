package org.computemetrics.parser;

import org.computemetrics.beans.ConditionalBean;
import org.eclipse.jdt.core.dom.ConditionalExpression;

public class ConditionalExpressionParser {

    public static ConditionalBean parse(ConditionalExpression conditionalExpression) {
        ConditionalBean conditionalBean = new ConditionalBean();
        conditionalBean.setExpression(conditionalExpression.getExpression().toString());
        conditionalBean.setThenExpression(conditionalExpression.getThenExpression().toString());
        conditionalBean.setElseExpression(conditionalExpression.getElseExpression().toString());
        conditionalBean.setNode(conditionalExpression);
        return conditionalBean;
    }
}
