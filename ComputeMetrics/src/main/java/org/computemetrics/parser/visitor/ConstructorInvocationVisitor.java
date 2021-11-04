package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ConstructorInvocation;

import java.util.ArrayList;
import java.util.List;

public class ConstructorInvocationVisitor extends ASTVisitor {
    private final List<ConstructorInvocation> calls = new ArrayList<>();

    public List<ConstructorInvocation> getCalls() {
        return calls;
    }

    @Override
    public boolean visit(ConstructorInvocation node) {
        calls.add(node);
        return super.visit(node);
    }
}
