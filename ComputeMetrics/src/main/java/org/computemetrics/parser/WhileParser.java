package org.computemetrics.parser;

import org.computemetrics.beans.WhileBean;
import org.eclipse.jdt.core.dom.WhileStatement;

public class WhileParser {

    public static WhileBean parse(WhileStatement whileStatement) {
        WhileBean whileBean = new WhileBean();
        whileBean.setExpression(whileStatement.getExpression().toString());
        whileBean.setBody(whileStatement.getBody().toString());
        whileBean.setNode(whileStatement);
        return whileBean;
    }
}
