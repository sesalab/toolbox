package org.computemetrics.parser;

import org.computemetrics.beans.AssertBean;
import org.eclipse.jdt.core.dom.AssertStatement;

public class AssertParser {

    public static AssertBean parse(AssertStatement assertStatement) {
        AssertBean assertBean = new AssertBean();
        assertBean.setExpression(assertStatement.getExpression().toString());
        assertBean.setMessage(assertStatement.getMessage() != null ? assertStatement.getMessage().toString() : null);
        assertBean.setNode(assertStatement);
        return assertBean;
    }
}
