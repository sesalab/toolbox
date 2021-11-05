package org.computemetrics.parser;

import org.computemetrics.beans.ThisConstructorCallBean;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.Expression;

import java.util.List;
import java.util.stream.Collectors;

public class ThisConstructorCallParser {

    public static ThisConstructorCallBean parse(ConstructorInvocation constructorInvocation) {
        ThisConstructorCallBean thisConstructorCallBean = new ThisConstructorCallBean();
        thisConstructorCallBean.setArguments(((List<Expression>) constructorInvocation.arguments()).stream()
                .map(Object::toString)
                .collect(Collectors.toList())
        );
        thisConstructorCallBean.setNode(constructorInvocation);
        return thisConstructorCallBean;
    }
}
