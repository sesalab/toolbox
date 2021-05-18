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

    public static MethodBean parse(MethodDeclaration pMethodNode, Collection<InstanceVariableBean> pClassInstanceVariableBeans) {
        MethodBean methodBean = new MethodBean();
        methodBean.setName(pMethodNode.getName().toString());
        methodBean.setParameters(pMethodNode.parameters());
        methodBean.setReturnType(pMethodNode.getReturnType2());
        methodBean.setTextContent(pMethodNode.toString());

        if (pMethodNode.toString().contains("private ")) {
            methodBean.setVisibility(0);
        } else if (pMethodNode.toString().contains("protected ")) {
            methodBean.setVisibility(1);
        } else methodBean.setVisibility(2);

        Collection<String> names = new HashSet<>();
        pMethodNode.accept(new NameVisitor(names));
        Collection<InstanceVariableBean> usedInstanceVariableBeans = getUsedInstanceVariable(names, pClassInstanceVariableBeans);
        methodBean.setUsedInstanceVariables(usedInstanceVariableBeans);

        Collection<String> invocations = new HashSet<>();
        pMethodNode.accept(new InvocationVisitor(invocations));
        Collection<MethodBean> invocationBeans = new ArrayList<>();
        for (String invocation : invocations) {
            invocationBeans.add(InvocationParser.parse(invocation));
        }
        methodBean.setMethodCalls(invocationBeans);
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
