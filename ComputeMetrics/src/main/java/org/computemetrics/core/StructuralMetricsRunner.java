package org.computemetrics.core;

import org.apache.commons.io.FileUtils;
import org.computemetrics.beans.ClassBean;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StructuralMetricsRunner extends MetricsRunner {
    private final StructuralInput input;
    private static final String LOC = "LOC";
    private static final String SLOC = "SLOC";
    private static final String NOA = "NOA";
    private static final String NOPubA = "NOPubA";
    private static final String NOPrivA = "NOPrivA";
    private static final String NOM = "NOM";
    private static final String WMC = "WMC";
    private static final String CBO = "CBO";
    private static final String HV = "HV";
    private static final String HD = "HD";
    private static final String HE = "HE";
    private static final String MI = "MI";
    private static final String RFC = "RFC";
    private static final String DIT = "DIT";
    private static final String NOC = "NOC";
    private static final String NAO = "NAO";
    private static final String NOO = "NOO";
    private static final String LCOM1 = "LCOM1";
    private static final String LCOM2 = "LCOM2";
    private static final String LCOM5 = "LCOM5";
    private static final String TCC = "TCC";
    private static final String LCC = "LCC";

    public StructuralMetricsRunner(StructuralInput input) {
        this.input = input;
    }

    @Override
    public List<Output> run() throws Exception {
        // Parse all .java files in input.getDirectory()
        Collection<File> javaFiles = FileUtils.listFiles(new File(input.getDirectory()), new String[]{"java"}, true);
        Collection<ClassBean> classBeans = new HashSet<>();
        for (File javaFile : javaFiles) {
            Path filePath = javaFile.toPath().toAbsolutePath();
            ClassBean classBean = pathToBean(filePath);
            classBeans.add(classBean);
        }

        List<Output> outputs = new ArrayList<>();
        Path pathToDirectory = Paths.get(input.getDirectory());
        String project = pathToDirectory.getFileName().toString();
        if (input.getFile() != null) {
            ClassBean classBean = classBeans.stream()
                    .filter(cb -> cb.getPathToFile().equals(pathToDirectory.resolve(Paths.get(input.getFile()))))
                    .findFirst().orElse(null);
            if (classBean == null) {
                throw new RuntimeException("Target file not found. Exiting.");
            }
            classBeans.clear();
            classBeans.add(classBean);
        }
        for (ClassBean classBean : classBeans) {
            String classFQN = classBean.getBelongingPackage() != null ? classBean.getBelongingPackage() + "." + classBean.getName() : classBean.getName();
            Map<String, Double> metrics = computeMetrics(classBean, classBeans);
            //Path filePath = Paths.get(input.getDirectory()).relativize(classBean.getPathToFile());
            Map<String, String> attributes = new HashMap<>();
            attributes.put("project", project);
            attributes.put("class", classFQN);
            outputs.add(new Output(attributes, metrics));
        }
        return outputs;
    }

    private Map<String, Double> computeMetrics(ClassBean classBean, Collection<ClassBean> classBeans) {
        Map<String, Double> metrics = new LinkedHashMap<>();
        for (String metric : input.getMetrics()) {
            switch (metric) {
                case LOC:
                    metrics.put(LOC, (double) ClassMetrics.getLOC(classBean));
                    break;
                case SLOC:
                    metrics.put(SLOC, (double) ClassMetrics.getSLOC(classBean));
                    break;
                case NOA:
                    metrics.put(NOA, (double) ClassMetrics.getNOA(classBean));
                    break;
                case NOPubA:
                    metrics.put(NOPubA, (double) ClassMetrics.getNOPubA(classBean));
                    break;
                case NOPrivA:
                    metrics.put(NOPrivA, (double) ClassMetrics.getNOPrivA(classBean));
                    break;
                case NOM:
                    metrics.put(NOM, (double) ClassMetrics.getNOM(classBean));
                    break;
                case WMC:
                    metrics.put(WMC, (double) ClassMetrics.getWMC(classBean));
                    break;
                case CBO:
                    metrics.put(CBO, (double) ClassMetrics.getCBO(classBean));
                    break;
                case HV:
                    metrics.put(HV, ClassMetrics.getHV(classBean));
                    break;
                case HD:
                    metrics.put(HD, ClassMetrics.getHD(classBean));
                    break;
                case HE:
                    metrics.put(HE, ClassMetrics.getHE(classBean));
                    break;
                case MI:
                    metrics.put(MI, ClassMetrics.getMI(classBean));
                    break;
                case RFC:
                    metrics.put(RFC, (double) ClassMetrics.getRFC(classBean));
                    break;
                case DIT:
                    metrics.put(DIT, (double) ClassMetrics.getDIT(classBean, classBeans));
                    break;
                case NOC:
                    metrics.put(NOC, (double) ClassMetrics.getNOC(classBean, classBeans));
                    break;
                case NAO:
                    metrics.put(NAO, (double) ClassMetrics.getNAO(classBean, classBeans));
                    break;
                case NOO:
                    metrics.put(NOO, (double) ClassMetrics.getNOO(classBean, classBeans));
                    break;
                case LCOM1:
                    metrics.put(LCOM1, (double) ClassMetrics.getLCOM1(classBean));
                    break;
                case LCOM2:
                    metrics.put(LCOM2, (double) ClassMetrics.getLCOM2(classBean));
                    break;
                case LCOM5:
                    metrics.put(LCOM5, ClassMetrics.getLCOM5(classBean));
                    break;
                case TCC:
                    metrics.put(TCC, ClassMetrics.getTCC(classBean));
                    break;
                case LCC:
                    metrics.put(LCC, ClassMetrics.getLCC(classBean));
                    break;
            }
        }
        return metrics;
    }
}
