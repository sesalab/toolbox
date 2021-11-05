package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.Assignment;

import java.util.Objects;

public class AssignmentBean extends NodeBean<Assignment> {
    private String leftHandSide;
    private String operator;
    private String rightHandSide;

    public String getLeftHandSide() {
        return leftHandSide;
    }

    public void setLeftHandSide(String leftHandSide) {
        this.leftHandSide = leftHandSide;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRightHandSide() {
        return rightHandSide;
    }

    public void setRightHandSide(String rightHandSide) {
        this.rightHandSide = rightHandSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentBean that = (AssignmentBean) o;
        return Objects.equals(leftHandSide, that.leftHandSide) && Objects.equals(operator, that.operator) && Objects.equals(rightHandSide, that.rightHandSide);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", leftHandSide, operator, rightHandSide);
    }
}
