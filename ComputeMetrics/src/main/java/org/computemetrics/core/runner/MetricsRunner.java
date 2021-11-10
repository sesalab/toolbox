package org.computemetrics.core.runner;

import org.computemetrics.beans.ClassBean;
import org.computemetrics.core.Output;
import org.computemetrics.parser.ClassParser;
import org.computemetrics.parser.CodeParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class MetricsRunner {

    public abstract List<Output> run() throws Exception;

    protected ClassBean pathToBean(Path filePath) throws IOException {
        CompilationUnit fileCU;
        try {
            fileCU = new CodeParser().createParser(Files.readString(filePath));
        } catch (IOException e) {
            throw new IOException("Could not read file " + filePath);
        }
        if (fileCU != null && fileCU.types().size() == 0) {
            throw new RuntimeException("No types found in file " + filePath);
        } else {
            TypeDeclaration typeDeclaration = (TypeDeclaration) fileCU.types().get(0);
            List<String> imports = new ArrayList<>();
            for (Object anImport : fileCU.imports()) {
                imports.add(anImport.toString());
            }
            ClassBean classBean = ClassParser.parse(typeDeclaration, fileCU.getPackage(), imports);
            classBean.setPathToFile(filePath.toAbsolutePath());
            return classBean;
        }
    }
}
