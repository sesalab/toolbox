package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;

import java.util.ArrayList;
import java.util.List;

public class AssignmentVisitor extends ASTVisitor {
    private final List<Assignment> assignments = new ArrayList<>();

    public List<Assignment> getAssignments() {
        return assignments;
    }

    @Override
    public boolean visit(Assignment node) {
        assignments.add(node);
        return super.visit(node);
    }
}
