package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MethodBean {

    private ClassBean belongingClass;
    private String name;
    private int visibility; // 0 means private, 1 means protected, 2 means public
    private String textContent;
    private Collection<InstanceVariableBean> usedInstanceVariables;
    private Collection<MethodBean> methodCalls;
    private Type returnType;
    private List<SingleVariableDeclaration> parameters;
    private MethodDeclaration methodDeclaration;

    public MethodBean() {
        usedInstanceVariables = new ArrayList<>();
        methodCalls = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String pTextContent) {
        textContent = pTextContent;
    }

    public Collection<InstanceVariableBean> getUsedInstanceVariables() {
        return usedInstanceVariables;
    }

    public void setUsedInstanceVariables(Collection<InstanceVariableBean> pUsedInstanceVariables) {
        usedInstanceVariables = pUsedInstanceVariables;
    }

    public void addUsedInstanceVariables(InstanceVariableBean pInstanceVariable) {
        usedInstanceVariables.add(pInstanceVariable);
    }

    public void removeUsedInstanceVariables(InstanceVariableBean pInstanceVariable) {
        usedInstanceVariables.remove(pInstanceVariable);
    }

    public Collection<MethodBean> getMethodCalls() {
        return methodCalls;
    }

    public void setMethodCalls(Collection<MethodBean> pMethodCalls) {
        methodCalls = pMethodCalls;
    }

    public void addMethodCalls(MethodBean pMethodCall) {
        methodCalls.add(pMethodCall);
    }

    public void removeMethodCalls(MethodBean pMethodCall) {
        methodCalls.remove(pMethodCall);
    }

    public String toString() {

        String string =
                "(" + name + "|" +
                        (textContent.length() > 10 ? textContent.replace("\n", " ").replace("\t", "").substring(0, 10).concat("...") : "") + "|";

        for (InstanceVariableBean usedInstanceVariable : usedInstanceVariables)
            string += usedInstanceVariable.getName() + ",";
        string = string.substring(0, string.length() - 1);
        string += "|";

        for (MethodBean methodCall : methodCalls)
            string += methodCall.getName() + ",";
        string = string.substring(0, string.length() - 1);
        string += ")";

        return string;

    }

    public int compareTo(Object o) {
        return this.getName().compareTo(((MethodBean) o).getName());
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public List<SingleVariableDeclaration> getParameters() {
        return parameters;
    }

    public void setParameters(List<SingleVariableDeclaration> parameters) {
        this.parameters = parameters;
    }

    public ClassBean getBelongingClass() {
        return belongingClass;
    }

    public void setBelongingClass(ClassBean belongingClass) {
        this.belongingClass = belongingClass;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
    }

    public boolean equals(Object arg) {
        return (this.getName().equals(((MethodBean) arg).getName()));
    }

}
