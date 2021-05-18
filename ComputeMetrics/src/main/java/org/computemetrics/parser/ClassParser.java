package org.computemetrics.parser;

import org.computemetrics.beans.ClassBean;
import org.computemetrics.beans.InstanceVariableBean;
import org.computemetrics.beans.MethodBean;
import org.computemetrics.parser.visitor.InstanceVariableVisitor;
import org.computemetrics.parser.visitor.MethodVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClassParser {

    public static ClassBean parse(TypeDeclaration pClassNode, String belongingPackage, List<String> imports) {
        ClassBean classBean = new ClassBean();
        classBean.setName(pClassNode.getName().toString());
        classBean.setImports(imports);
        classBean.setBelongingPackage(belongingPackage);
        classBean.setTextContent(pClassNode.toString());
        if (pClassNode.getSuperclassType() != null)
            classBean.setSuperclass(pClassNode.getSuperclassType().toString());
        else
            classBean.setSuperclass(null);

        String[] lines = Pattern.compile("\n").split(pClassNode.toString());
        classBean.setLOC(lines.length);

        Collection<FieldDeclaration> instanceVariableNodes = new ArrayList<>();
        pClassNode.accept(new InstanceVariableVisitor(instanceVariableNodes));
        Collection<InstanceVariableBean> instanceVariables = instanceVariableNodes.stream()
                .map(InstanceVariableParser::parse)
                .collect(Collectors.toList());
        classBean.setInstanceVariables(instanceVariables);

        Collection<MethodDeclaration> methods = new ArrayList<>();
        pClassNode.accept(new MethodVisitor(methods));

        Collection<String> getMethods = new ArrayList<>();
        Collection<String> setMethods = new ArrayList<>();
        Collection<MethodBean> methodBeans = new ArrayList<>();
        for (MethodDeclaration method : methods) {
            String methodName = method.getName().toString();
            if (methodName.contains("get")) {
                getMethods.add(methodName);
            }
            if (methodName.contains("set")) {
                setMethods.add(methodName);
            }
            methodBeans.add(MethodParser.parse(method, instanceVariables));
        }

        int numberOfGetterOrSetter = 0;
        for (InstanceVariableBean instanceVariable : instanceVariables) {
            for (String getMethod : getMethods) {
                if (instanceVariable.getName().equalsIgnoreCase(getMethod.substring(3))) {
                    numberOfGetterOrSetter++;
                }
            }
            for (String setMethod : setMethods) {
                if (instanceVariable.getName().equalsIgnoreCase(setMethod.substring(3))) {
                    numberOfGetterOrSetter++;
                }
            }
        }
        classBean.setNumberOfGetterAndSetter(numberOfGetterOrSetter);

        for (MethodBean classMethod : methodBeans) {
            Collection<MethodBean> classMethodInvocations = classMethod.getMethodCalls();
            Collection<MethodBean> definedInvocations = new ArrayList<>(classMethodInvocations);
            classMethod.setMethodCalls(definedInvocations);
        }
        classBean.setMethods(methodBeans);
        return classBean;
    }

    private static MethodBean isInto(MethodBean pClassMethodInvocation, Collection<MethodBean> pMethodBeans) {
        for (MethodBean methodBean : pMethodBeans) {
            if (methodBean.getName().equals(pClassMethodInvocation.getName())) {
                return methodBean;
            }
        }
        return null;
    }
}
