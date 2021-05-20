package org.computemetrics.core;

import org.computemetrics.beans.ClassBean;
import org.computemetrics.beans.InstanceVariableBean;
import org.computemetrics.beans.MethodBean;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
     * Computes Source Lines of Code, that is LOC without comment lines, of a class.
     *
     * @param cb
     * @return
     */
    public static int getSLOC(ClassBean cb) {
        int sloc = 0;
        String[] lines = Pattern.compile("\r\n|\r|\n").split(cb.getTextContent());
        for (String line : lines) {
            boolean matched = Pattern.compile("\\*|//|/\\*(\\*)?").matcher(line.trim()).find();
            if (!matched) {
                sloc++;
            }
        }
        return sloc;
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
    public static int getNOPubA(ClassBean cb) {
        return (int) cb.getInstanceVariables().stream()
                .filter(v -> v.getVisibility().equals("public"))
                .count();
    }

    /**
     * Computes Number of Public Attributes (fields or instance variables) of a class.
     *
     * @param cb
     * @return
     */
    public static int getNOPrivA(ClassBean cb) {
        return (int) cb.getInstanceVariables().stream()
                .filter(v -> v.getVisibility().equals("private"))
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

    /**
     * Computes Depth of Inheritance of a class.
     *
     * @param cb
     * @param classes
     * @return
     */
    public static int getDIT(ClassBean cb, Collection<ClassBean> classes) {
        ClassBean superClass = getSuperclassBean(cb, classes);
        if (superClass == null) {
            return 1;
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
    public static int getNOC(ClassBean cb, Collection<ClassBean> classes) {
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
    public static int getNAO(ClassBean cb, Collection<ClassBean> classes) {
        ClassBean superClass = getSuperclassBean(cb, classes);
        if (superClass == null) {
            return 0;
        }
        int noa = 0;
        for (MethodBean method : cb.getMethods()) {
            if (matchSuperMethod(method, superClass) == null) {
                noa++;
                ;
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
    public static int getNOO(ClassBean cb, Collection<ClassBean> classes) {
        ClassBean superClass = getSuperclassBean(cb, classes);
        if (superClass == null) {
            return 0;
        }
        int noo = 0;
        for (MethodBean method : cb.getMethods()) {
            if (matchSuperMethod(method, superClass) != null) {
                noo++;
                ;
            }
        }
        return noo;
    }

    /**
     * Computes Lack of Cohesion of Method sversion 1 (Chidamber and Kemerer)
     *
     * @param cb
     * @return
     */
    public static int getLCOM1(ClassBean cb) {
        int notShare = 0;
        List<MethodBean> methods = (List<MethodBean>) cb.getMethods();
        for (int i = 0; i < methods.size() - 1; i++) {
            for (int j = i + 1; j < methods.size(); j++) {
                MethodBean methodA = methods.get(i);
                MethodBean methodB = methods.get(j);
                if (!shareAnInstanceVariable(methodA, methodB)) {
                    notShare++;
                }
            }
        }
        return notShare;
    }

    /**
     * Computes Lack of Cohesion of Methods version 2 (Chidamber and Kemerer)
     *
     * @param cb
     * @return
     */
    public static int getLCOM2(ClassBean cb) {
        int share = 0;
        int notShare = 0;
        List<MethodBean> methods = (List<MethodBean>) cb.getMethods();
        for (int i = 0; i < methods.size() - 1; i++) {
            for (int j = i + 1; j < methods.size(); j++) {
                MethodBean methodA = methods.get(i);
                MethodBean methodB = methods.get(j);
                if (shareAnInstanceVariable(methodA, methodB)) {
                    share++;
                } else {
                    notShare++;
                }
            }
        }
        if (notShare > share) {
            return notShare - share;
        }
        return 0;
    }

    public static double getLCOM5(ClassBean cb) {
        int numberOfAttributes = cb.getInstanceVariables().size();
        int numberPossibleLinks = cb.getMethods().size() * numberOfAttributes;
        if (numberPossibleLinks == 0) {
            return 0;
        }
        int numberActualLinks = 0;
        for (MethodBean method : cb.getMethods()) {
            numberActualLinks += method.getUsedInstanceVariables().size();
        }
        return (float) (numberActualLinks - numberPossibleLinks) / (numberOfAttributes - numberPossibleLinks);
    }

    //TODO Implement Halsteads metrics

    public static double getTCC(ClassBean cb) {
        List<MethodBean> visibleMethods = cb.getMethods().stream().filter(m -> m.getVisibility() != 0).collect(Collectors.toList());
        int possibleConnections = visibleMethods.size() * (visibleMethods.size() - 1) / 2;
        if (possibleConnections <= 1) {
            return 0;
        }
        int directConnections = 0;
        for (int i = 0; i < visibleMethods.size() - 1; i++) {
            for (int j = i + 1; j < visibleMethods.size(); j++) {
                MethodBean methodA = visibleMethods.get(i);
                MethodBean methodB = visibleMethods.get(j);
                if (shareAnInstanceVariable(methodA, methodB) || existDirectDependency(methodA, methodB)) {
                    directConnections++;
                }
            }
        }
        return (float) directConnections / possibleConnections;
    }

    public static double getLCC(ClassBean cb) {
        List<MethodBean> visibleMethods = cb.getMethods().stream().filter(m -> m.getVisibility() != 0).collect(Collectors.toList());
        int possibleConnections = visibleMethods.size() * (visibleMethods.size() - 1) / 2;
        if (possibleConnections <= 1) {
            return 0;
        }
        int indirectConnections = 0;
        for (int i = 0; i < visibleMethods.size() - 1; i++) {
            for (int j = i + 1; j < visibleMethods.size(); j++) {
                MethodBean methodA = visibleMethods.get(i);
                MethodBean methodB = visibleMethods.get(j);
                if (existIndirectDependency(methodA, methodB)) {
                    indirectConnections++;
                }
            }
        }
        return getTCC(cb) + (float) indirectConnections / possibleConnections;
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

    private static ClassBean getSuperclassBean(ClassBean cb, Collection<ClassBean> classes) {
        return classes.stream()
                .filter(c -> c.getName().equals(cb.getSuperclass()))
                .findFirst().orElse(null);
    }

    private static MethodBean matchSuperMethod(MethodBean method, ClassBean superClass) {
        List<MethodBean> sameNameMethods = superClass.getMethods().stream().filter(m -> m.equals(method)).collect(Collectors.toList());
        return sameNameMethods.stream().filter(m -> m.getParameters().size() == method.getParameters().size()).findFirst().orElse(null);
    }

    private static boolean shareAnInstanceVariable(MethodBean m1, MethodBean m2) {
        for (InstanceVariableBean i : m1.getUsedInstanceVariables()) {
            if (m2.getUsedInstanceVariables().contains(i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean existDirectDependency(MethodBean m1, MethodBean m2) {
        for (MethodBean methodCall : m1.getMethodCalls()) {
            String name = methodCall.getName();
            if (name.equals(m2.getName())) {
                int size = methodCall.getParameters().size();
                if (size == m2.getParameters().size()) {
                    return true;
                }
            }
        }
        for (MethodBean methodCall : m2.getMethodCalls()) {
            String name = methodCall.getName();
            if (name.equals(m1.getName())) {
                int size = methodCall.getParameters().size();
                if (size == m1.getParameters().size()) {
                    return true;
                }
            }
        }
        return false;
        /*
        return m1.getMethodCalls().stream()
                .anyMatch(mc -> mc.getName().equals(m2.getName()) &&
                        (mc.getParameters().size() == m2.getParameters().size()))
                ||
                m2.getMethodCalls().stream()
                        .anyMatch(mc -> mc.getName().equals(m1.getName()) &&
                                (mc.getParameters().size() == m1.getParameters().size()));
         */
    }

    private static Collection<MethodBean> getCalledMethods(MethodBean mb) {
        Set<MethodBean> fullCalledMethods = new HashSet<>();
        Set<MethodBean> calledMethods = new HashSet<>();
        calledMethods.add(mb);
        while (true) {
            Set<MethodBean> newCalledMethods = new HashSet<>();
            for (MethodBean calledMethod : calledMethods) {
                newCalledMethods.addAll(calledMethod.getMethodCalls());
            }
            if (fullCalledMethods.containsAll(newCalledMethods)) {
                return fullCalledMethods;
            }
            fullCalledMethods.addAll(newCalledMethods);
            calledMethods = newCalledMethods;
        }
    }

    private static boolean existIndirectDependency(MethodBean m1, MethodBean m2) {
        return getCalledMethods(m1).contains(m2) || getCalledMethods(m2).contains(m1);
    }

}
