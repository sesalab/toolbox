package org.computemetrics.core;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.computemetrics.beans.ClassBean;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;

import java.util.ArrayList;
import java.util.List;

public class ChangeMetrics {

    public static Pair<Integer, Integer> computeAddedRemovedConditions(ClassBean beforeCB, ClassBean afterCB) {
        // Asserts
        List<AssertStatement> beforeAsserts = beforeCB.getAsserts();
        List<AssertStatement> afterAsserts = afterCB.getAsserts();
        Pair<Integer, Integer> addedRemovedAsserts = computeAddedRemovedNodes(beforeAsserts, afterAsserts);

        // Ifs
        List<IfStatement> beforeIfs = beforeCB.getIfs();
        List<IfStatement> afterIfs = afterCB.getIfs();
        Pair<Integer, Integer> addedRemovedIfs = computeAddedRemovedNodes(beforeIfs, afterIfs);

        // Switch Cases
        List<SwitchCase> beforeSwitchCases = beforeCB.getSwitchCases();
        List<SwitchCase> afterSwitchCases = afterCB.getSwitchCases();
        Pair<Integer, Integer> addedRemovedSwitchCases = computeAddedRemovedNodes(beforeSwitchCases, afterSwitchCases);

        // Whiles
        List<WhileStatement> beforeWhiles = beforeCB.getWhiles();
        List<WhileStatement> afterWhiles = afterCB.getWhiles();
        Pair<Integer, Integer> addedRemovedWhiles = computeAddedRemovedNodes(beforeWhiles, afterWhiles);

        // Fors
        List<ForStatement> beforeFors = beforeCB.getFors();
        List<ForStatement> afterFors = afterCB.getFors();
        Pair<Integer, Integer> addedRemovedFors = computeAddedRemovedNodes(beforeFors, afterFors);

        // Conditionals
        List<ConditionalExpression> beforeConditionals = beforeCB.getConditionals();
        List<ConditionalExpression> afterConditionals = afterCB.getConditionals();
        Pair<Integer, Integer> addedRemovedConditionals = computeAddedRemovedNodes(beforeConditionals, afterConditionals);

        // TODO Fix: equality test is not good because the toString() prints the entire content, not the statements alone: create specific polymorph methods
        System.out.println(addedRemovedAsserts);
        System.out.println(beforeIfs);
        System.out.println(addedRemovedIfs);
        System.out.println(addedRemovedSwitchCases);
        System.out.println(addedRemovedWhiles);
        System.out.println(addedRemovedFors);
        System.out.println(addedRemovedConditionals);

        int addedConditions = addedRemovedAsserts.getLeft() + addedRemovedIfs.getLeft() + addedRemovedSwitchCases.getLeft() +
                addedRemovedWhiles.getLeft() + addedRemovedFors.getLeft() + addedRemovedConditionals.getLeft();
        int removedConditions = addedRemovedAsserts.getRight() + addedRemovedIfs.getRight() + addedRemovedSwitchCases.getRight() +
                addedRemovedWhiles.getRight() + addedRemovedFors.getRight() + addedRemovedConditionals.getRight();
        return new ImmutablePair<>(addedConditions, removedConditions);
    }

    public static int computeAddedCalls(ClassBean beforeCB, ClassBean afterCB) {
        // TODO Stub
        return 0;
    }

    public static int computeRemovedCalls(ClassBean beforeCB, ClassBean afterCB) {
        // TODO Stub
        return 0;
    }

    public static int computeAddedAssignments(ClassBean beforeCB, ClassBean afterCB) {
        // TODO Stub
        return 0;
    }

    public static int computeRemovedAssignments(ClassBean beforeCB, ClassBean afterCB) {
        // TODO Stub
        return 0;
    }

    private static Pair<Integer, Integer> computeAddedRemovedNodes(List<? extends ASTNode> beforeNodes, List<? extends ASTNode> afterNodes) {
        List<ASTNode> removedNodes = new ArrayList<>();
        List<ASTNode> addedNodes = new ArrayList<>(afterNodes);
        for (ASTNode beforeNode : beforeNodes) {
            for (ASTNode afterNode : afterNodes) {
                if (beforeNode.toString().equals(afterNode.toString())) {
                    addedNodes.remove(afterNode);
                    break;
                }
            }
            removedNodes.add(beforeNode);
        }
        int removedCount = removedNodes.size();
        int addedCount = addedNodes.size();
        return new ImmutablePair<>(removedCount, addedCount);
    }
}
