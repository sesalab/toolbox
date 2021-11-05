package org.computemetrics.core;

import org.apache.commons.lang3.tuple.Pair;
import org.computemetrics.beans.ClassBean;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChangeMetricsRunner extends MetricsRunner {
    private final ChangeInput input;
    private static final String COND = "Cond";
    private static final String CALL = "Call";
    private static final String ASSIGN = "Assign";

    private static final String ADDED_COND = "added_conditionals";
    private static final String REMOVED_COND = "removed_conditionals";
    private static final String ADDED_CALL = "added_calls";
    private static final String REMOVED_CALL = "removed_calls";
    private static final String ADDED_ASSIGN = "added_assignments";
    private static final String REMOVED_ASSIGN = "removed_assignments";

    public ChangeMetricsRunner(ChangeInput input) {
        this.input = input;
    }

    @Override
    public List<Output> run() throws Exception {
        ClassBean beforeClassBean = pathToBean(Paths.get(input.getBeforeFile()));
        ClassBean afterClassBean = pathToBean(Paths.get(input.getAfterFile()));
        Map<String, Double> metrics = computeMetrics(beforeClassBean, afterClassBean);
        Map<String, String> attributes = new HashMap<>();
        attributes.put("before_file", input.getBeforeFile());
        attributes.put("after_file", input.getAfterFile());
        Output output = new Output(attributes, metrics);
        List<Output> outputs = new ArrayList<>();
        outputs.add(output);
        return outputs;
    }

    private Map<String, Double> computeMetrics(ClassBean beforeClassBean, ClassBean afterClassBean) {
        Map<String, Double> metrics = new LinkedHashMap<>();
        for (String metric : input.getMetrics()) {
            switch (metric) {
                case COND:
                    Pair<Integer, Integer> addedRemovedConditions = ChangeMetrics.computeAddedRemovedConditions(beforeClassBean, afterClassBean);
                    metrics.put(ADDED_COND, (double) addedRemovedConditions.getLeft());
                    metrics.put(REMOVED_COND, (double) addedRemovedConditions.getRight());
                    break;
                case CALL:
                    Pair<Integer, Integer> addedRemovedCalls = ChangeMetrics.computeAddedRemovedCalls(beforeClassBean, afterClassBean);
                    metrics.put(ADDED_CALL, (double) addedRemovedCalls.getLeft());
                    metrics.put(REMOVED_CALL, (double) addedRemovedCalls.getRight());
                    break;
                case ASSIGN:
                    Pair<Integer, Integer> addedRemovedAssignments = ChangeMetrics.computeAddedRemovedAssignments(beforeClassBean, afterClassBean);
                    metrics.put(ADDED_ASSIGN, (double) addedRemovedAssignments.getLeft());
                    metrics.put(REMOVED_ASSIGN, (double) addedRemovedAssignments.getRight());
                    break;
            }
        }
        return metrics;
    }
}
