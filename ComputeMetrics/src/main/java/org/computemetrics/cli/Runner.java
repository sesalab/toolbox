package org.computemetrics.cli;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.computemetrics.core.ChangeInput;
import org.computemetrics.core.ChangeMetricsRunner;
import org.computemetrics.core.MetricsRunner;
import org.computemetrics.core.StructuralMetricsRunner;
import org.computemetrics.core.StructuralInput;
import org.computemetrics.core.Output;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Runner {
    public static final String PROJECT = "project";
    public static final String CLASS = "class";

    public static final String DIR_MODE = "dir";
    public static final String DIFF_MODE = "diff";

    public static final String MODE_OPT = "mode";
    public static final String DIR_OPT = "dir";
    public static final String FILE_OPT = "file";
    public static final String METRICS_OPT = "metrics";
    public static final String BEFORE_OPT = "before";
    public static final String AFTER_OPT = "after";

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(new Option("" + MODE_OPT.charAt(0), MODE_OPT, true, "Running mode (diff/dir)"));
        options.addOption(new Option("" + DIR_OPT.charAt(0), DIR_OPT, true, "If DIR mode is set, this represents the base directory where looking for files to analyze"));
        options.addOption(new Option("" + FILE_OPT.charAt(0), FILE_OPT, true, "If DIR mode is set, this represents the specific file to analyze (optional)"));
        options.addOption(new Option("" + Character.toUpperCase(METRICS_OPT.charAt(0)), METRICS_OPT, true, "List of comma-separated metric acronyms to compute"));
        options.addOption(new Option("" + BEFORE_OPT.charAt(0), BEFORE_OPT, true, "If DIFF mode is set, this represents the old version of the file to analyze"));
        options.addOption(new Option("" + AFTER_OPT.charAt(0), AFTER_OPT, true, "If DIFF mode is set, this represents the new version of the file to analyze"));

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cl = parser.parse(options, args);
            if (!cl.hasOption(MODE_OPT)) {
                exitError("Running mode not specified: please, specify either 'diff' or 'dir'. Exiting.");
            }
            String mode = cl.getOptionValue(MODE_OPT);
            if (!mode.equalsIgnoreCase(DIR_MODE) && !mode.equalsIgnoreCase(DIFF_MODE)) {
                exitError("Invalid running mode: please, specify either 'diff' or 'dir'. Exiting.");
            }

            if (!cl.hasOption(METRICS_OPT)) {
                exitError("No metrics specified. Exiting.");
            }
            String metrics = cl.getOptionValue(METRICS_OPT);

            MetricsRunner runner = null;
            if (mode.equalsIgnoreCase(DIR_MODE)) {
                if (!cl.hasOption(DIR_OPT)) {
                    exitError("Base directory not specified. Exiting.");
                }
                String dir = cl.getOptionValue(DIR_OPT);
                String file = cl.getOptionValue(FILE_OPT);
                runner = new StructuralMetricsRunner(new StructuralInput(dir, file, metrics.split(",")));
            } else if (mode.equalsIgnoreCase(DIFF_MODE)) {
                if (!cl.hasOption(BEFORE_OPT) || !cl.hasOption(AFTER_OPT)) {
                    exitError("Files versions not specified: please, specify using -b and -a options. Exiting.");
                }
                String beforeFile = cl.getOptionValue(BEFORE_OPT);
                String afterFile = cl.getOptionValue(AFTER_OPT);
                runner = new ChangeMetricsRunner(new ChangeInput(beforeFile, afterFile, metrics.split(",")));
            }
            List<Output> outputs = runner.run();
            List<Map<String, Object>> content = new ArrayList<>();
            for (Output output : outputs) {
                Map<String, Object> contentLine = new LinkedHashMap<>();
                contentLine.putAll(output.getAttributes());
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
        } catch (Exception e) {
            e.printStackTrace();
            exitError("Unexpected error. Exiting.");
        }
    }

    private static void exitError(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
