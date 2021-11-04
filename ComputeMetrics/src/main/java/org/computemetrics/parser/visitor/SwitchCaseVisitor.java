package org.computemetrics.parser.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SwitchCase;

import java.util.ArrayList;
import java.util.List;

public class SwitchCaseVisitor extends ASTVisitor {
    private final List<SwitchCase> cases = new ArrayList<>();

    public List<SwitchCase> getCases() {
        return cases;
    }

    @Override
    public boolean visit(SwitchCase node) {
        cases.add(node);
        return super.visit(node);
    }
}
