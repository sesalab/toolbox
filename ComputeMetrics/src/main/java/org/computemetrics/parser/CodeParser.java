package org.computemetrics.parser;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;


public class CodeParser {
    private char[] charClass;
    private CompilationUnit unit;
    private ASTParser parser;

    public CodeParser() {

    }

    public CodeParser(String pClassToAnalyze) {
        this.charClass = pClassToAnalyze.toCharArray();
    }


    /**
     * This method allows to create a parser for the AST;
     *
     * @param a String representation of a Class;
     * @return a CompilationUnit to work on;
     */
    public CompilationUnit createParser() {
        parser = ASTParser.newParser(AST.JLS4);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(this.charClass); // set source
        parser.setResolveBindings(true); // we need bindings later on
        return (CompilationUnit) parser.createAST(null);
    }

    public TypeDeclaration createParser(String pMethod, int pType) {
        parser = ASTParser.newParser(AST.JLS4);
        parser.setKind(pType);
        parser.setSource(pMethod.toCharArray()); // set source

        return (TypeDeclaration) parser.createAST(null);
    }

    public CompilationUnit createParser(String pClass) {
        parser = ASTParser.newParser(AST.JLS4);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(pClass.toCharArray()); // set source
        parser.setResolveBindings(true); // we need bindings later on
        return (CompilationUnit) parser.createAST(null);
    }

    /*
    public CompilationUnit createParser(String pClass, Collection<File> javaFiles) {
        parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(pClass.toCharArray()); // set source
        String[] sourcepathEntries = javaFiles.stream().map(File::getAbsolutePath).toArray(String[]::new);
        parser.setEnvironment(new String[]{"/tmp/com.google.android.divideandconquer/src/com/google/android/divideandconquer"}, sourcepathEntries, null, true);
        parser.setResolveBindings(true); // we need bindings later on
        return (CompilationUnit) parser.createAST(null);
    }
     */

    public CompilationUnit getCompilationUnit() {
        return this.unit;
    }
}