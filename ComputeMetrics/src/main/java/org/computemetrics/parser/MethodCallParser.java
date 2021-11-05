package org.computemetrics.parser;

import org.computemetrics.beans.MethodCallBean;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

import java.util.List;
import java.util.stream.Collectors;

public class MethodCallParser {

    public static MethodCallBean parse(MethodInvocation methodInvocation) {
        MethodCallBean methodCallBean = new MethodCallBean();
        methodCallBean.setCaller(methodInvocation.getExpression() != null ? methodInvocation.getExpression().toString() : null);
        methodCallBean.setCalled(methodInvocation.getName().getIdentifier());
        methodCallBean.setArguments(((List<Expression>) methodInvocation.arguments()).stream()
                .map(Object::toString)
                .collect(Collectors.toList())
        );
        methodCallBean.setNode(methodInvocation);
        return methodCallBean;
    }
}
