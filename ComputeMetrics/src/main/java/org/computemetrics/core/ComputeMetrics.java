package org.computemetrics.core;

import org.apache.commons.io.FileUtils;
import org.computemetrics.beans.ClassBean;
import org.computemetrics.parser.ClassParser;
import org.computemetrics.parser.CodeParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ComputeMetrics {
    private final Input input;
    private static final String LOC = "LOC";
    private static final String SLOC = "SLOC";
    private static final String NOA = "NOA";
    private static final String NOPubA = "NOPubA";
    private static final String NOPrivA = "NOPrivA";
    private static final String NOM = "NOM";
    private static final String WMC = "WMC";
    private static final String CBO = "CBO";
    private static final String RFC = "RFC";
    private static final String DIT = "DIT";
    private static final String NOC = "NOC";
    private static final String NAO = "NAO";
    private static final String NOO = "NOO";
    private static final String LCOM1 = "LCOM1";
    private static final String LCOM2 = "LCOM2";

    public ComputeMetrics(Input input) {
        this.input = input;
    }

    public Output run() throws IOException {
        // Parse all .java files in input.getDirectory()
        Collection<File> javaFiles = FileUtils.listFiles(new File(input.getDirectory()), new String[]{"java"}, true);
        Collection<ClassBean> classBeans = new HashSet<>();
        for (File javaFile : javaFiles) {
            Path filePath = javaFile.toPath().toAbsolutePath();
            CompilationUnit fileCU = new CodeParser().createParser(new String(Files.readAllBytes(filePath)));
            if (fileCU.types().size() == 0) {
                System.err.println("Could not parse file: " + filePath);
            } else {
                TypeDeclaration typeDeclaration = (TypeDeclaration) fileCU.types().get(0);
                List<String> imports = new ArrayList<>();
                for (Object anImport : fileCU.imports()) {
                    imports.add(anImport.toString());
                }
                ClassBean classBean = ClassParser.parse(typeDeclaration, fileCU.getPackage().getName().getFullyQualifiedName(), imports);
                classBean.setPathToClass(filePath.toAbsolutePath().toString());
                classBeans.add(classBean);
            }
        }

        Path targetFilePath = Paths.get(input.getDirectory(), input.getFile()).toAbsolutePath();
        ClassBean classBean = classBeans.stream()
                .filter(cb -> cb.getPathToClass().equals(targetFilePath.toString()))
                .findFirst().orElse(null);
        if (classBean == null) {
            throw new RuntimeException("Target file not found: aborting...");
        }
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
            }
        }
        return new Output(input.getDirectory(), input.getFile(), metrics);
    }
}
