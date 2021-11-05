package org.computemetrics.parser;

import org.computemetrics.beans.AssignmentBean;
import org.eclipse.jdt.core.dom.Assignment;

public class AssignmentParser {

    public static AssignmentBean parse(Assignment assignment) {
        AssignmentBean assignmentBean = new AssignmentBean();
        assignmentBean.setLeftHandSide(assignment.getLeftHandSide().toString());
        assignmentBean.setOperator(assignment.getOperator().toString());
        assignmentBean.setRightHandSide(assignment.getRightHandSide().toString());
        assignmentBean.setNode(assignment);
        return assignmentBean;
    }
}
