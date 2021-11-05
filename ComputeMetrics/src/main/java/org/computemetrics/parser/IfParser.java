package org.computemetrics.parser;

import org.computemetrics.beans.IfBean;
import org.eclipse.jdt.core.dom.IfStatement;

public class IfParser {

    public static IfBean parse(IfStatement ifStatement) {
        IfBean ifBean = new IfBean();
        ifBean.setExpression(ifStatement.getExpression().toString());
        ifBean.setThenStatement(ifStatement.getThenStatement() != null ? ifStatement.getThenStatement().toString() : null);
        ifBean.setElseStatement(ifStatement.getElseStatement() != null ? ifStatement.getElseStatement().toString() : null);
        ifBean.setNode(ifStatement);
        return ifBean;
    }
}
