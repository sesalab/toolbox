package org.computemetrics.core;

import org.computemetrics.beans.ClassBean;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChangeMetricsRunner extends MetricsRunner {
    private final ChangeInput input;
    private static final String METH = "Meth";
    private static final String COND = "Cond";
    private static final String CALL = "Call";
    private static final String ASSIGN = "Assign";

    private static final String ADDED_METH = "added_methods";
    private static final String REMOVED_METH = "removed_methods";
    private static final String CHANGED_METH = "changed_methods";
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
                /*
                case METH:
                    metrics.put(ADDED_METH, (double) ChangeMetrics.computeAddedMethods(beforeClassBean, afterClassBean));
                    metrics.put(REMOVED_METH, (double) ChangeMetrics.computeRemovedMethods(beforeClassBean, afterClassBean));
                    metrics.put(CHANGED_METH, (double) ChangeMetrics.computeChangedMethods(beforeClassBean, afterClassBean));
                    break;
                 */
                case COND:
                    metrics.put(ADDED_COND, (double) ChangeMetrics.computeAddedConditions(beforeClassBean, afterClassBean));
                    metrics.put(REMOVED_COND, (double) ChangeMetrics.computeRemovedConditions(beforeClassBean, afterClassBean));
                    break;
                case CALL:
                    metrics.put(ADDED_CALL, (double) ChangeMetrics.computeAddedCalls(beforeClassBean, afterClassBean));
                    metrics.put(REMOVED_CALL, (double) ChangeMetrics.computeRemovedCalls(beforeClassBean, afterClassBean));
                    break;
                case ASSIGN:
                    metrics.put(ADDED_ASSIGN, (double) ChangeMetrics.computeAddedAssignments(beforeClassBean, afterClassBean));
                    metrics.put(REMOVED_ASSIGN, (double) ChangeMetrics.computeRemovedAssignments(beforeClassBean, afterClassBean));
                    break;
            }
        }
        return metrics;
    }
}
