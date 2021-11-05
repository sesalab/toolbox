package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;

import java.util.ArrayList;
import java.util.List;

public class SuperConstructorInvocationVisitor extends ASTVisitor {
    private final List<SuperConstructorInvocation> calls = new ArrayList<>();

    public List<SuperConstructorInvocation> getCalls() {
        return calls;
    }

    @Override
    public boolean visit(SuperConstructorInvocation node) {
        calls.add(node);
        return super.visit(node);
    }
}
