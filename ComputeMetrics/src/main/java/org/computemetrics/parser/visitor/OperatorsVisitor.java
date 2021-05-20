package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import java.util.HashMap;
import java.util.Map;

public class OperatorsVisitor extends ASTVisitor {
    private static final String EQUALITY = "=";
    private final Map<String, Integer> operators = new HashMap<>();

    public Map<String, Integer> getOperators() {
        return operators;
    }

    public void add(String key) {
        operators.merge(key, 1, Integer::sum);
    }

    public boolean visit(InfixExpression node) {
        add(node.getOperator().toString());
        return super.visit(node);
    }

    public boolean visit(PostfixExpression node) {
        add(node.getOperator().toString());
        return super.visit(node);
    }

    public boolean visit(PrefixExpression node) {
        add(node.getOperator().toString());
        return super.visit(node);
    }

    public boolean visit(Assignment node) {
        add(node.getOperator().toString());
        return super.visit(node);
    }

    public boolean visit(SingleVariableDeclaration node) {
        add(EQUALITY);
        return super.visit(node);
    }

    public boolean visit(VariableDeclarationFragment node) {
        add(EQUALITY);
        return super.visit(node);
    }
}
