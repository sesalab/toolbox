package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.WhileStatement;

import java.util.ArrayList;
import java.util.List;

public class WhileVisitor extends ASTVisitor {
    private final List<WhileStatement> whiles = new ArrayList<>();

    public List<WhileStatement> getWhiles() {
        return whiles;
    }

    @Override
    public boolean visit(WhileStatement node) {
        whiles.add(node);
        return super.visit(node);
    }
}
