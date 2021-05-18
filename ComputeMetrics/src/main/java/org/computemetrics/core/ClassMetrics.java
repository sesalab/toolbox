package org.computemetrics.core;

import org.computemetrics.beans.ClassBean;
import org.computemetrics.beans.InstanceVariableBean;
import org.computemetrics.beans.MethodBean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class ClassMetrics {

    /**
     * Computes Lines of Code of a class.
     *
     * @param cb
     * @return
     */
    public static int getLOC(ClassBean cb) {
        return cb.getLOC();
    }

    /**
     * Computes Effective Lines of Code, that is LOC without empty lines, of a class.
     *
     * @param cb
     * @return
     */
    public static int getELOC(ClassBean cb) {
        return cb.getTextContent().split("\r\n|\r|\n").length;
    }

    /**
     * Computes Number of Attributes (fields or instance variables) of a class.
     *
     * @param cb
     * @return
     */
    public static int getNOA(ClassBean cb) {
        return cb.getInstanceVariables().size();
    }

    /**
     * Computes Number of Public Attributes (fields or instance variables) of a class.
     *
     * @param cb
     * @return
     */
    public static int getNOPA(ClassBean cb) {
        return (int) cb.getInstanceVariables().stream()
                .filter(v -> v.getVisibility().equals("public"))
                .count();
    }

    /**
     * Computes Number of Methods
     *
     * @param cb
     * @return
     */
    public static int getNOM(ClassBean cb) {
        return cb.getMethods().size();
    }

    /**
     * Computes Weighted Methods per Class, that is the sum of complexities (e.g., McCabe Cyclomatic Complexity) of all methods of a class.
     *
     * @param cb
     * @return
     */
    public static int getWMC(ClassBean cb) {
        return cb.getMethods().stream()
                .map(ClassMetrics::getMcCabeCyclomaticComplexity)
                .reduce(0, Integer::sum);
    }

    /**
     * Computes McCabe Cyclomatic Complexity of a method.
     *
     * @param mb
     * @return
     */
    public static int getMcCabeCyclomaticComplexity(MethodBean mb) {
        List<String> regexes = Arrays.asList("return",
                "if",
                "else",
                "case",
                "for",
                "while",
                "break",
                "&&",
                "||",
                "catch",
                "throw"
        );
        int mcCabe = 0;
        for (String regex : regexes) {
            if (Pattern.compile(regex).matcher(mb.getTextContent()).find()) {
                mcCabe++;
            }
        }
        return mcCabe;
    }

    /**
     * Computes Coupling Between Objects, that is the number of referenced classes (also known as Fan-out).
     *
     * @param cb
     * @return
     */
    public static int getCBO(ClassBean cb) {
        return cb.getImports().size();
    }

    /**
     * Computes Message Passing Coupling (MPC) between two classes.
     *
     * @param c1
     * @param c2
     * @return
     */
    public static int getMPC(ClassBean c1, ClassBean c2) {
        int c1MethodCalls = getNumTotalMethodCalls(c1);
        int c2MethodCalls = getNumTotalMethodCalls(c2);
        if (c1MethodCalls + c2MethodCalls != 0) {
            int mpcC1 = getOneSideMPC(c1, c2);
            int mpcC2 = getOneSideMPC(c2, c1);
            return (mpcC1 + mpcC2) / (getNumTotalMethodCalls(c1) + getNumTotalMethodCalls(c2));
        } else {
            return 0;
        }
    }

    /**
     * Computes part of Message Passing Coupling (MPC), that is the number of method calls from a class to another.
     *
     * @param c1
     * @param c2
     * @return
     */
    private static int getOneSideMPC(ClassBean c1, ClassBean c2) {
        int mpc = 0;
        for (MethodBean method : c1.getMethods()) {
            for (MethodBean calledMethod : method.getMethodCalls()) {
                for (MethodBean methodC2 : c2.getMethods()) {
                    if (calledMethod.getName().equalsIgnoreCase(methodC2.getName())) {
                        mpc++;
                    }
                }
            }
        }
        return mpc;
    }

    /**
     * Compute total number of outgoing method calls (outgoing messages)
     *
     * @param cb
     * @return
     */
    private static int getNumTotalMethodCalls(ClassBean cb) {
        return cb.getMethods().stream()
                .map(m -> m.getMethodCalls().size())
                .reduce(0, Integer::sum);
    }

    /**
     * Computes Response for a Class, that is the number of distinct outgoing messages and number of methods.
     *
     * @param cb
     * @return
     */
    public static int getRFC(ClassBean cb) {
        Set<MethodBean> distinctMethodCalls = new HashSet<>();
        for (MethodBean m : cb.getMethods()) {
            distinctMethodCalls.addAll(m.getMethodCalls());
        }
        return distinctMethodCalls.size() + cb.getMethods().size();
    }

    private static ClassBean getSuperclass(ClassBean cb, List<ClassBean> classes) {
        return classes.stream()
                .filter(c -> c.getName().equals(cb.getSuperclass()))
                .findFirst().orElse(null);
    }

    /**
     * Computes Depth of Inheritance of a class.
     *
     * @param cb
     * @param classes
     * @return
     */
    public static int getDIT(ClassBean cb, List<ClassBean> classes) {
        ClassBean superClass = getSuperclass(cb, classes);
        if (superClass == null) {
            return 0;
        }
        int superDit = getDIT(superClass, classes);
        return superDit + 1;
    }

    /**
     * Computes Number of Children of a class.
     *
     * @param cb
     * @param classes
     * @return
     */
    public static int getNOC(ClassBean cb, List<ClassBean> classes) {
        int noc = 0;
        for (ClassBean c : classes) {
            if (c.getSuperclass() != null && c.getSuperclass().equals(cb.getName())) {
                noc++;
            }
        }
        return noc;
    }

    /**
     * Computes Number of Added Operations, that is the number of new methods added by a subclass.
     *
     * @param cb
     * @param classes
     * @return
     */
    public static int getNAO(ClassBean cb, List<ClassBean> classes) {
        ClassBean superClass = getSuperclass(cb, classes);
        if (superClass == null) {
            return 0;
        }
        int noa = 0;
        for (MethodBean method : cb.getMethods()) {
            if (!superClass.getMethods().contains(method)) {
                noa++;
            }
        }
        return noa;
    }

    /**
     * Computes Number of Overridden Operations, that is the number of overridden methods.
     *
     * @param cb
     * @param classes
     * @return
     */
    public static int getNOO(ClassBean cb, List<ClassBean> classes) {
        ClassBean superClass = getSuperclass(cb, classes);
        if (superClass == null) {
            return 0;
        }
        int noo = 0;
        for (MethodBean method : cb.getMethods()) {
            if (superClass.getMethods().contains(method)) {
                noo++;
            }
        }
        return noo;
    }

    /**
     * Computes Lack of Cohesion of Method version 1 (Chidamber and Kemerer)
     *
     * @param cb
     * @return
     */
    public static int getLCOM1(ClassBean cb) {
        int notShare = 0;
        List<MethodBean> methods = (List<MethodBean>) cb.getMethods();
        for (int i = 0; i < methods.size(); i++) {
            for (int j = i + 1; j < methods.size(); j++) {
                if (!shareAnInstanceVariable(methods.get(i), methods.get(j))) {
                    notShare++;
                }
            }
        }
        return notShare;
    }

    /**
     * Computes Lack of Cohesion of Method version 2
     *
     * @param cb
     * @return
     */
    public static int getLCOM2(ClassBean cb) {
        int share = 0;
        int notShare = 0;
        List<MethodBean> methods = (List<MethodBean>) cb.getMethods();
        for (int i = 0; i < methods.size(); i++) {
            for (int j = i + 1; j < methods.size(); j++) {
                if (shareAnInstanceVariable(methods.get(i), methods.get(j))) {
                    share++;
                } else {
                    notShare++;
                }
            }
        }
        if (share > notShare) {
            return 0;
        } else {
            return (notShare - share);
        }
    }

    private static boolean shareAnInstanceVariable(MethodBean m1, MethodBean m2) {
        for (InstanceVariableBean i : m1.getUsedInstanceVariables()) {
            if (m2.getUsedInstanceVariables().contains(i)) {
                return true;
            }
        }
        return false;
    }
}
