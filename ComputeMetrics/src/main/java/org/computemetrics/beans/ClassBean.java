package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ClassBean {
    private String name;
    private Collection<InstanceVariableBean> instanceVariables;
    private Collection<MethodBean> methods;
    private Collection<String> imports;
    private String textContent;
    private int LOC;
    private String superclass;
    private String belongingPackage;
    private Path pathToFile;
    private int entityClassUsage;
    private Map<String, Integer> operands;
    private Map<String, Integer> operators;
    private List<AssertBean> asserts;
    private List<IfBean> ifs;
    private List<SwitchCaseBean> switchCases;
    private List<WhileBean> whiles;
    private List<ForBean> fors;
    private List<ConditionalBean> conditionals;
    private List<MethodCallBean> methodCalls;
    private List<ThisConstructorCallBean> thisConstructorCalls;
    private List<SuperConstructorCallBean> superConstructorCalls;
    private List<AssignmentBean> assignments;
    private TypeDeclaration typeDeclaration;

    public int getLOC() {
        return LOC;
    }

    public void setLOC(int lOC) {
        LOC = lOC;
    }

    public ClassBean() {
        instanceVariables = new ArrayList<>();
        methods = new ArrayList<>();
        imports = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public Collection<InstanceVariableBean> getInstanceVariables() {
        return instanceVariables;
    }

    public void setInstanceVariables(Collection<InstanceVariableBean> pInstanceVariables) {
        instanceVariables = pInstanceVariables;
    }

    public void addInstanceVariables(InstanceVariableBean pInstanceVariable) {
        instanceVariables.add(pInstanceVariable);
    }

    public void removeInstanceVariables(InstanceVariableBean pInstanceVariable) {
        instanceVariables.remove(pInstanceVariable);
    }

    public Collection<MethodBean> getMethods() {
        return methods;
    }

    public void setMethods(Collection<MethodBean> pMethods) {
        methods = pMethods;
    }

    public void addMethod(MethodBean pMethod) {
        methods.add(pMethod);
    }

    public void removeMethod(MethodBean pMethod) {
        methods.remove(pMethod);
    }

    public String toString() {
        return
                "name = " + name + "\n" +
                        "instanceVariables = " + instanceVariables + "\n" +
                        "methods = " + methods + "\n";
    }

    public int compareTo(Object pClassBean) {
        return this.getName().compareTo(((ClassBean) pClassBean).getName());
    }

    public Collection<String> getImports() {
        return imports;
    }

    public void setImports(Collection<String> imports) {
        this.imports = imports;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getSuperclass() {
        return superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public String getBelongingPackage() {
        return belongingPackage;
    }

    public void setBelongingPackage(String belongingPackage) {
        this.belongingPackage = belongingPackage;
    }

    public int getEntityClassUsage() {
        return entityClassUsage;
    }

    public Path getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(Path pathToFile) {
        this.pathToFile = pathToFile;
    }

    public void setNumberOfGetterAndSetter(int entityClassUsage) {
        this.entityClassUsage = entityClassUsage;
    }

    public Map<String, Integer> getOperands() {
        return operands;
    }

    public void setOperands(Map<String, Integer> operands) {
        this.operands = operands;
    }

    public Map<String, Integer> getOperators() {
        return operators;
    }

    public void setOperators(Map<String, Integer> operators) {
        this.operators = operators;
    }

    public int getNumberOfOperands() {
        return operands.values().stream().reduce(Integer::sum).orElse(0);
    }

    public int getNumberOfDistinctOperands() {
        return operands.keySet().size();
    }

    public int getNumberOfOperators() {
        return operators.values().stream().reduce(Integer::sum).orElse(0);
    }

    public int getNumberOfDistinctOperators() {
        return operators.keySet().size();
    }

    public List<AssertBean> getAsserts() {
        return asserts;
    }

    public void setAsserts(List<AssertBean> asserts) {
        this.asserts = asserts;
    }

    public List<IfBean> getIfs() {
        return ifs;
    }

    public void setIfs(List<IfBean> ifs) {
        this.ifs = ifs;
    }

    public List<SwitchCaseBean> getSwitchCases() {
        return switchCases;
    }

    public void setSwitchCases(List<SwitchCaseBean> switchCases) {
        this.switchCases = switchCases;
    }

    public List<WhileBean> getWhiles() {
        return whiles;
    }

    public void setWhiles(List<WhileBean> whiles) {
        this.whiles = whiles;
    }

    public List<ForBean> getFors() {
        return fors;
    }

    public void setFors(List<ForBean> fors) {
        this.fors = fors;
    }

    public List<ConditionalBean> getConditionals() {
        return conditionals;
    }

    public void setConditionals(List<ConditionalBean> conditionals) {
        this.conditionals = conditionals;
    }

    public List<MethodCallBean> getMethodCalls() {
        return methodCalls;
    }

    public void setMethodCalls(List<MethodCallBean> methodCalls) {
        this.methodCalls = methodCalls;
    }

    public List<ThisConstructorCallBean> getThisConstructorCalls() {
        return thisConstructorCalls;
    }

    public void setThisConstructorCalls(List<ThisConstructorCallBean> thisConstructorCalls) {
        this.thisConstructorCalls = thisConstructorCalls;
    }

    public List<SuperConstructorCallBean> getSuperConstructorCalls() {
        return superConstructorCalls;
    }

    public void setSuperConstructorCalls(List<SuperConstructorCallBean> superConstructorCalls) {
        this.superConstructorCalls = superConstructorCalls;
    }

    public List<AssignmentBean> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentBean> assignments) {
        this.assignments = assignments;
    }

    public TypeDeclaration getTypeDeclaration() {
        return typeDeclaration;
    }

    public void setTypeDeclaration(TypeDeclaration typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }

    public boolean equals(Object arg) {
        return this.getName().equals(((ClassBean) arg).getName()) &&
                this.getBelongingPackage().equals(((ClassBean) arg).getBelongingPackage());
    }

}
