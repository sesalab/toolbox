package it.sesalab.computemetrics.parser;

import it.sesalab.computemetrics.beans.ClassBean;
import it.sesalab.computemetrics.beans.InstanceVariableBean;
import it.sesalab.computemetrics.beans.MethodBean;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;

public class ClassSerializer {

    public static void serialize(ClassBean pClassBean, String path) throws FileNotFoundException {

        // Instantiate the writer
        PrintWriter out = new PrintWriter(path + pClassBean.getName() + ".java");

        // Write the class definition
        out.write("public class " + pClassBean.getName() + " {\n\n");

        // Write the instance variables
        for (InstanceVariableBean instanceVariable : pClassBean.getInstanceVariables())
            out.write(
                    "\t" +
                            instanceVariable.getVisibility() + " " +
                            instanceVariable.getType() + " " +
                            instanceVariable.getName() +
                            (instanceVariable.getInitialization() != null ? " = " + instanceVariable.getInitialization() : "") + ";\n");
        out.write("\n");

        // Write the methods
        for (MethodBean methodBean : pClassBean.getMethods())
            out.write("\t" + methodBean.getTextContent().replace("\n  ", "\n\t\t").replace("}\n", "\t}\n") + "\n");

        // Write the end of the class definition
        out.write("}");

        // Close the writer
        out.close();

    }

    public static void serialize(Collection<ClassBean> pClassBeans, String path) throws FileNotFoundException {
        for (ClassBean classBean : pClassBeans)
            serialize(classBean, path);
    }

}
