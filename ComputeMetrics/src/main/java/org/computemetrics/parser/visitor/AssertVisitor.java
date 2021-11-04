package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AssertStatement;

import java.util.ArrayList;
import java.util.List;

public class AssertVisitor extends ASTVisitor {
    private final List<AssertStatement> asserts = new ArrayList<>();

    public List<AssertStatement> getAsserts() {
        return asserts;
    }

    @Override
    public boolean visit(AssertStatement node) {
        asserts.add(node);
        return super.visit(node);
    }
}