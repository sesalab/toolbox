package org.computemetrics.parser;

import org.computemetrics.beans.ForBean;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ForStatement;

import java.util.List;
import java.util.stream.Collectors;

public class ForParser {

    public static ForBean parse(ForStatement forStatement) {
        ForBean forBean = new ForBean();
        forBean.setInizializers(((List<Expression>) forStatement.initializers()).stream()
                .map(Object::toString)
                .collect(Collectors.toList())
        );

        forBean.setExpression(forStatement.getExpression() != null ? forStatement.getExpression().toString() : null);
        forBean.setUpdaters(((List<Expression>) forStatement.updaters()).stream()
                .map(Object::toString)
                .collect(Collectors.toList())
        );
        forBean.setBody(forStatement.getBody().toString());
        forBean.setNode(forStatement);
        return forBean;
    }
}
