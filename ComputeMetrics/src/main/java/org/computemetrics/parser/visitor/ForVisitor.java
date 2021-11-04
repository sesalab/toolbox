package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ForStatement;

import java.util.ArrayList;
import java.util.List;

public class ForVisitor extends ASTVisitor {
    private final List<ForStatement> fors = new ArrayList<>();

    public List<ForStatement> getFors() {
        return fors;
    }

    @Override
    public boolean visit(ForStatement node) {
        fors.add(node);
        return super.visit(node);
    }
}
