package org.computemetrics.parser;

import org.computemetrics.beans.SuperConstructorCallBean;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;

import java.util.List;
import java.util.stream.Collectors;

public class SuperConstructorCallParser {

    public static SuperConstructorCallBean parse(SuperConstructorInvocation superConstructorInvocation) {
        SuperConstructorCallBean superConstructorCallBean = new SuperConstructorCallBean();
        superConstructorCallBean.setArguments(((List<Expression>) superConstructorInvocation.arguments()).stream()
                .map(Object::toString)
                .collect(Collectors.toList())
        );
        superConstructorCallBean.setNode(superConstructorInvocation);
        return superConstructorCallBean;
    }
}
