package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IfStatement;

import java.util.ArrayList;
import java.util.List;

public class IfVisitor extends ASTVisitor {
    private final List<IfStatement> ifs = new ArrayList<>();

    public List<IfStatement> getIfs() {
        return ifs;
    }

    @Override
    public boolean visit(IfStatement node) {
        ifs.add(node);
        return super.visit(node);
    }
}
