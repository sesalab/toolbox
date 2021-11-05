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

        // Whiles
        List<WhileBean> beforeWhiles = beforeCB.getWhiles();
        List<WhileBean> afterWhiles = afterCB.getWhiles();
        Pair<Integer, Integer> addedRemovedWhiles = computeAddedRemovedNodes(beforeWhiles, afterWhiles);

        // Fors
        List<ForBean> beforeFors = beforeCB.getFors();
        List<ForBean> afterFors = afterCB.getFors();
        Pair<Integer, Integer> addedRemovedFors = computeAddedRemovedNodes(beforeFors, afterFors);

        // Conditionals
        List<ConditionalBean> beforeConditionals = beforeCB.getConditionals();
        List<ConditionalBean> afterConditionals = afterCB.getConditionals();
        Pair<Integer, Integer> addedRemovedConditionals = computeAddedRemovedNodes(beforeConditionals, afterConditionals);

        System.out.println(addedRemovedAsserts);
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
