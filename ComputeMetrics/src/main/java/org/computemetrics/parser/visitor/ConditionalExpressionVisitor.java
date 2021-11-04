package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ConditionalExpression;

import java.util.ArrayList;
import java.util.List;

public class ConditionalExpressionVisitor extends ASTVisitor {
    private final List<ConditionalExpression> conditionals = new ArrayList<>();

    public List<ConditionalExpression> getConditionals() {
        return conditionals;
    }

    @Override
    public boolean visit(ConditionalExpression node) {
        conditionals.add(node);
        return super.visit(node);
    }
}
