package org.computemetrics.core;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.computemetrics.beans.*;

import java.util.ArrayList;
import java.util.List;

public class ChangeMetrics {

    public static Pair<Integer, Integer> computeAddedRemovedConditions(ClassBean beforeCB, ClassBean afterCB) {
        // Asserts
        List<AssertBean> beforeAsserts = beforeCB.getAsserts();
        List<AssertBean> afterAsserts = afterCB.getAsserts();
        Pair<Integer, Integer> addedRemovedAsserts = computeAddedRemovedNodes(beforeAsserts, afterAsserts);

        // Ifs
        List<IfBean> beforeIfs = beforeCB.getIfs();
        List<IfBean> afterIfs = afterCB.getIfs();
        Pair<Integer, Integer> addedRemovedIfs = computeAddedRemovedNodes(beforeIfs, afterIfs);

        // Switch Cases
        List<SwitchCaseBean> beforeSwitchCases = beforeCB.getSwitchCases();
        List<SwitchCaseBean> afterSwitchCases = afterCB.getSwitchCases();
        Pair<Integer, Integer> addedRemovedSwitchCases = computeAddedRemovedNodes(beforeSwitchCases, afterSwitchCases);

        // Whiles (no infinite loops)
        List<WhileBean> beforeWhiles = beforeCB.getWhiles();
        List<WhileBean> afterWhiles = afterCB.getWhiles();
        beforeWhiles.removeIf(WhileBean::isInfiniteLoop);
        afterWhiles.removeIf(WhileBean::isInfiniteLoop);
        Pair<Integer, Integer> addedRemovedWhiles = computeAddedRemovedNodes(beforeWhiles, afterWhiles);

        // Fors (no infinite loops)
        List<ForBean> beforeFors = beforeCB.getFors();
        List<ForBean> afterFors = afterCB.getFors();
        beforeFors.removeIf(ForBean::isInfiniteLoop);
        afterFors.removeIf(ForBean::isInfiniteLoop);
        Pair<Integer, Integer> addedRemovedFors = computeAddedRemovedNodes(beforeFors, afterFors);

        // Conditionals
        List<ConditionalBean> beforeConditionals = beforeCB.getConditionals();
        List<ConditionalBean> afterConditionals = afterCB.getConditionals();
        Pair<Integer, Integer> addedRemovedConditionals = computeAddedRemovedNodes(beforeConditionals, afterConditionals);

        int addedConditions = addedRemovedAsserts.getLeft() + addedRemovedIfs.getLeft() + addedRemovedSwitchCases.getLeft() +
                addedRemovedWhiles.getLeft() + addedRemovedFors.getLeft() + addedRemovedConditionals.getLeft();
        int removedConditions = addedRemovedAsserts.getRight() + addedRemovedIfs.getRight() + addedRemovedSwitchCases.getRight() +
                addedRemovedWhiles.getRight() + addedRemovedFors.getRight() + addedRemovedConditionals.getRight();
        return new ImmutablePair<>(addedConditions, removedConditions);
    }

    public static Pair<Integer, Integer> computeAddedRemovedCalls(ClassBean beforeCB, ClassBean afterCB) {
        // Method Calls
        List<MethodCallBean> beforeMethodCalls = beforeCB.getMethodCalls();
        List<MethodCallBean> afterMethodCalls = afterCB.getMethodCalls();
        Pair<Integer, Integer> addedRemovedCalls = computeAddedRemovedNodes(beforeMethodCalls, afterMethodCalls);

        // This Constructor Calls
        List<ThisConstructorCallBean> beforeThisConstructorCalls = beforeCB.getThisConstructorCalls();
        List<ThisConstructorCallBean> afterThisConstructorCalls = afterCB.getThisConstructorCalls();
        Pair<Integer, Integer> addedRemovedThisConstructorCalls = computeAddedRemovedNodes(beforeThisConstructorCalls, afterThisConstructorCalls);

        // Super Constructor Calls
        List<SuperConstructorCallBean> beforeSuperConstructorCalls = beforeCB.getSuperConstructorCalls();
        List<SuperConstructorCallBean> afterSuperConstructorCalls = afterCB.getSuperConstructorCalls();
        Pair<Integer, Integer> addedRemovedSuperConstructorCalls = computeAddedRemovedNodes(beforeSuperConstructorCalls, afterSuperConstructorCalls);

        int addedCalls = addedRemovedCalls.getLeft() + addedRemovedThisConstructorCalls.getLeft() + addedRemovedSuperConstructorCalls.getLeft();
        int removedCalls = addedRemovedCalls.getRight() + addedRemovedThisConstructorCalls.getRight() + addedRemovedSuperConstructorCalls.getRight();
        return new ImmutablePair<>(addedCalls, removedCalls);
    }

    public static Pair<Integer, Integer> computeAddedRemovedAssignments(ClassBean beforeCB, ClassBean afterCB) {
        // Super Constructor Calls
        List<AssignmentBean> beforeAssignments = beforeCB.getAssignments();
        List<AssignmentBean> afterAssignments = afterCB.getAssignments();
        Pair<Integer, Integer> addedRemovedAssignments = computeAddedRemovedNodes(beforeAssignments, afterAssignments);

        return new ImmutablePair<>(addedRemovedAssignments.getLeft(), addedRemovedAssignments.getRight());
    }

    private static Pair<Integer, Integer> computeAddedRemovedNodes(List<? extends NodeBean<?>> beforeNodes, List<? extends NodeBean<?>> afterNodes) {
        List<NodeBean<?>> addedNodes = new ArrayList<>(afterNodes);
        List<NodeBean<?>> removedNodes = new ArrayList<>();
        OUTER_LOOP:
        for (NodeBean<?> beforeNode : beforeNodes) {
            for (NodeBean<?> afterNode : afterNodes) {
                if (beforeNode.equals(afterNode)) {
                    addedNodes.remove(afterNode);
                    continue OUTER_LOOP;
                }
            }
            removedNodes.add(beforeNode);
        }
        int addedCount = addedNodes.size();
        int removedCount = removedNodes.size();
        return new ImmutablePair<>(addedCount, removedCount);
    }
}
