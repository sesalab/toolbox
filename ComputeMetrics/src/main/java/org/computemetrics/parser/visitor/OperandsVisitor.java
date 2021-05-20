package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;

import java.util.HashMap;
import java.util.Map;

public class OperandsVisitor extends ASTVisitor {
    private static final String NULL = "null";
    private final Map<String, Integer> operands = new HashMap<>();

    public Map<String, Integer> getOperands() {
        return operands;
    }

    public void add(String key) {
        operands.merge(key, 1, Integer::sum);
    }

    public boolean visit(SimpleName node) {
        add(node.getIdentifier());
        return super.visit(node);
    }

    public boolean visit(NullLiteral node) {
        add(NULL);
        return super.visit(node);
    }

    public boolean visit(StringLiteral node) {
        add(node.getLiteralValue());
        return super.visit(node);
    }

    public boolean visit(CharacterLiteral node) {
        add(String.valueOf(node.charValue()));
        return super.visit(node);
    }

    public boolean visit(BooleanLiteral node) {
        add(String.valueOf(node.booleanValue()));
        return super.visit(node);
    }

    public boolean visit(NumberLiteral node) {
        add(node.getToken());
        return super.visit(node);
    }
}
