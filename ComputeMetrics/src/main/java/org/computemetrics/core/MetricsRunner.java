package org.computemetrics.core;

import org.computemetrics.beans.ClassBean;
import org.computemetrics.parser.ClassParser;
import org.computemetrics.parser.CodeParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class MetricsRunner {

    public abstract List<Output> run() throws Exception;

    protected ClassBean pathToBean(Path filePath) throws Exception {
        CompilationUnit fileCU = new CodeParser().createParser(new String(Files.readAllBytes(filePath)));
        if (fileCU.types().size() == 0) {
            throw new RuntimeException("Could not parse file: " + fileCU);
        } else {
            TypeDeclaration typeDeclaration = (TypeDeclaration) fileCU.types().get(0);
            List<String> imports = new ArrayList<>();
            for (Object anImport : fileCU.imports()) {
                imports.add(anImport.toString());
            }
            if (fileCU.getPackage() == null) {
                throw new RuntimeException("Could not parse file: " + fileCU);
            } else {
                ClassBean classBean = ClassParser.parse(typeDeclaration, fileCU.getPackage().getName().getFullyQualifiedName(), imports);
                classBean.setPathToFile(filePath.toAbsolutePath());
                return classBean;
            }
        }
    }
}
