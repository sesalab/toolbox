package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;

import java.util.ArrayList;
import java.util.List;

public class MethodInvocationVisitor extends ASTVisitor {
    private final List<MethodInvocation> calls = new ArrayList<>();

    public List<MethodInvocation> getCalls() {
        return calls;
    }

    @Override
    public boolean visit(MethodInvocation node) {
        calls.add(node);
        return super.visit(node);
    }
}