package org.computemetrics.core;

import org.computemetrics.beans.ClassBean;
import org.computemetrics.parser.ClassParser;
import org.computemetrics.parser.CodeParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ComputeMetrics {
    private final Input input;
    private static final String LOC = "LOC";
    private static final String ELOC = "ELOC";
    private static final String NOA = "NOA";
    private static final String NOPA = "NOPA";
    private static final String NOM = "NOM";
    private static final String WMC = "WMC";
    private static final String CBO = "CBO";
    private static final String RFC = "RFC";
    private static final String LCOM1 = "LCOM1";
    private static final String LCOM2 = "LCOM2";

    public ComputeMetrics(Input input) {
        this.input = input;
    }

    public Output run() throws IOException {
        Path filepath = Paths.get(input.getDirectory(), input.getFile()).toAbsolutePath();
        CompilationUnit targetCU = new CodeParser().createParser(new String(Files.readAllBytes(filepath)));
        if (targetCU.types().size() == 0) {
            throw new RuntimeException("Cannot parse " + filepath);
        }
        TypeDeclaration typeDeclaration = (TypeDeclaration) targetCU.types().get(0);
        List<String> imports = new ArrayList<>();
        for (Object anImport : targetCU.imports()) {
            imports.add(anImport.toString());
        }
        ClassBean classBean = ClassParser.parse(typeDeclaration, targetCU.getPackage().getName().getFullyQualifiedName(), imports);
        classBean.setPathToClass(filepath.toAbsolutePath().toString());
        Map<String, Double> metrics = new LinkedHashMap<>();
        for (String metric : input.getMetrics()) {
            switch (metric) {
                case LOC:
                    metrics.put(LOC, (double) ClassMetrics.getLOC(classBean));
                    break;
                case ELOC:
                    metrics.put(ELOC, (double) ClassMetrics.getELOC(classBean));
                    break;
                case NOA:
                    metrics.put(NOA, (double) ClassMetrics.getNOA(classBean));
                    break;
                case NOPA:
                    metrics.put(NOPA, (double) ClassMetrics.getNOPA(classBean));
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
                case RFC:
                    metrics.put(RFC, (double) ClassMetrics.getRFC(classBean));
                    break;
                case LCOM1:
                    metrics.put(LCOM1, (double) ClassMetrics.getLCOM1(classBean));
                    break;
                case LCOM2:
                    metrics.put(LCOM2, (double) ClassMetrics.getLCOM2(classBean));
                    break;
            }
        }
        return new Output(input.getDirectory(), input.getFile(), metrics);
    }
}
