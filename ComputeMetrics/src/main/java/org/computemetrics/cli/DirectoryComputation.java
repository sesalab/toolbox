package org.computemetrics.cli;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.computemetrics.core.ComputeMetrics;
import org.computemetrics.core.Input;
import org.computemetrics.core.Output;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DirectoryComputation {
    public static final String DIR = "dir";
    public static final String FILE = "file";
    public static final String METRICS = "metrics";

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(new Option("" + DIR.charAt(0), DIR, true, "Base directory where looking for files to analyze"));
        options.addOption(new Option("" + FILE.charAt(0), FILE, true, "(Optional) Source code file to analyze"));
        options.addOption(new Option("" + METRICS.charAt(0), METRICS, true, "Source code file to analyze"));

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cl = parser.parse(options, args);
            if (!cl.hasOption(DIR)) {
                exitError("Base directory not specified. Exiting.");
            }
            if (!cl.hasOption(METRICS)) {
                exitError("No metric specified. Exiting.");
            }
            String dir = cl.getOptionValue(DIR);
            String file = cl.getOptionValue(FILE);
            String metrics = cl.getOptionValue(METRICS);

            ComputeMetrics computeMetrics = new ComputeMetrics(new Input(dir, file, metrics.split(",")));
            List<Output> outputs = computeMetrics.run();
            List<Map<String, Object>> content = new ArrayList<>();
            for (Output output : outputs) {
                Map<String, Object> contentLine = new LinkedHashMap<>();
                contentLine.put(DIR, output.getDirectory());
                contentLine.put(FILE, output.getFile());
                contentLine.putAll(output.getMetrics());
                content.add(contentLine);
            }
            Gson gson = new Gson();
            Type gsonType = new TypeToken<List<Map<String, Object>>>() {
            }.getType();
            String gsonString = gson.toJson(content, gsonType);
            System.out.println(gsonString);
        } catch (ParseException e) {
            e.printStackTrace();
            exitError("Invalid options. Exiting.");
        } catch (IOException e) {
            e.printStackTrace();
            exitError("File not found. Exiting.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            exitError("Unexpected error. Exiting.");
        }
    }

    private static void exitError(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
