package org.computemetrics.parser;

import org.computemetrics.beans.InstanceVariableBean;
import org.computemetrics.beans.MethodBean;
import org.computemetrics.parser.visitor.InvocationVisitor;
import org.computemetrics.parser.visitor.NameVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class MethodParser {

    public static MethodBean parse(MethodDeclaration methodDeclaration, Collection<InstanceVariableBean> pClassInstanceVariableBeans) {
        MethodBean methodBean = new MethodBean();
        methodBean.setName(methodDeclaration.getName().toString());
        methodBean.setParameters(methodDeclaration.parameters());
        methodBean.setReturnType(methodDeclaration.getReturnType2());
        methodBean.setTextContent(methodDeclaration.toString());

        if (methodDeclaration.toString().contains("private ")) {
            methodBean.setVisibility(0);
        } else if (methodDeclaration.toString().contains("protected ")) {
            methodBean.setVisibility(1);
        } else methodBean.setVisibility(2);

        Collection<String> names = new HashSet<>();
        methodDeclaration.accept(new NameVisitor(names));
        Collection<InstanceVariableBean> usedInstanceVariableBeans = getUsedInstanceVariable(names, pClassInstanceVariableBeans);
        methodBean.setUsedInstanceVariables(usedInstanceVariableBeans);

        Collection<String> invocations = new HashSet<>();
        methodDeclaration.accept(new InvocationVisitor(invocations));
        Collection<MethodBean> invocationBeans = new ArrayList<>();
        for (String invocation : invocations) {
            invocationBeans.add(InvocationParser.parse(invocation));
        }
        methodBean.setMethodCalls(invocationBeans);
        methodBean.setMethodDeclaration(methodDeclaration);
        return methodBean;
    }

    private static Collection<InstanceVariableBean> getUsedInstanceVariable(Collection<String> pNames, Collection<InstanceVariableBean> pClassInstanceVariableBeans) {
        Collection<InstanceVariableBean> usedInstanceVariableBeans = new ArrayList<>();
        for (InstanceVariableBean classInstanceVariableBean : pClassInstanceVariableBeans) {
            if (pNames.remove(classInstanceVariableBean.getName())) {
                usedInstanceVariableBeans.add(classInstanceVariableBean);
            }
        }
        return usedInstanceVariableBeans;
    }
}
