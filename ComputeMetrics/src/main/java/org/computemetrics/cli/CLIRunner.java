package org.computemetrics.cli;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.computemetrics.core.Output;
import org.computemetrics.core.input.ChangeInput;
import org.computemetrics.core.input.FilesInput;
import org.computemetrics.core.runner.ChangeMetricsRunner;
import org.computemetrics.core.runner.MetricsRunner;
import org.computemetrics.core.runner.StructuralMetricsRunner;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CLIRunner {
    public static final String MODE_OPT = "mode";
    public static final String DIR_OPT = "dir";
    public static final String FILE_OPT = "file";
    public static final String FILES_OPT = "files";
    public static final String BEFORE_OPT = "beforeFile";
    public static final String AFTER_OPT = "afterFile";
    public static final String METRICS_OPT = "metrics";
    public static final String EXPORT_OPT = "export";

    public static final String DIR_MODE = "dir";
    public static final String DIFF_MODE = "diff";

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption(new Option("" + MODE_OPT.charAt(0), MODE_OPT, true, "Running mode (dir/diff)"));
        options.addOption(new Option("" + DIR_OPT.charAt(0), DIR_OPT, true, "If DIR mode is set, this represents the base directory where looking for files to analyze"));
        options.addOption(new Option("" + FILE_OPT.charAt(0), FILE_OPT, true, "If DIR mode is set, this represents the specific file to analyze (mutual exclusiong with -" + FILES_OPT + ")"));
        options.addOption(new Option("" + FILES_OPT, true, "If DIR mode is set, this represents a comma-separated files list to analyze (mutual exclusiong with -" + FILE_OPT + ")"));
        options.addOption(new Option("" + BEFORE_OPT.charAt(0), BEFORE_OPT, true, "If DIFF mode is set, this represents the old version of the file to analyze"));
        options.addOption(new Option("" + AFTER_OPT.charAt(0), AFTER_OPT, true, "If DIFF mode is set, this represents the new version of the file to analyze"));
        options.addOption(new Option("" + Character.toUpperCase(METRICS_OPT.charAt(0)), METRICS_OPT, true, "List of comma-separated metric acronyms to compute"));
        options.addOption(new Option("" + EXPORT_OPT.charAt(0), EXPORT_OPT, true, "Path to a file where to export the JSON result. if not specified the output will be printed to stdout (optional)"));

        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(options, args);
        String mode = cl.getOptionValue(MODE_OPT);
        String metrics = cl.getOptionValue(METRICS_OPT);

        if (mode == null) {
            throw new ParseException("Running mode not specified: please, specify either 'diff' or 'dir'. Exiting.");
        }
        if (!mode.equalsIgnoreCase(DIR_MODE) && !mode.equalsIgnoreCase(DIFF_MODE)) {
            throw new ParseException("Invalid running mode: please, specify either 'diff' or 'dir'. Exiting.");
        }
        if (metrics == null) {
            throw new ParseException("No metrics specified. Exiting.");
        }

        MetricsRunner runner = null;
        if (mode.equalsIgnoreCase(DIR_MODE)) {
            String dir = cl.getOptionValue(DIR_OPT);
            if (dir == null) {
                throw new ParseException("Base directory not specified. Exiting.");
            }
            String file = cl.getOptionValue(FILE_OPT);
            String files = cl.getOptionValue(FILES_OPT);
            if (file == null && files == null) {
                throw new ParseException("Please, specify either -" + FILE_OPT + " or -" + FILES_OPT + " options, not both. Exiting.");
            }
            List<String> filesToAnalyse = new ArrayList<>();
            if (file != null) {
                filesToAnalyse.add(file);
            } else {
                filesToAnalyse.addAll(List.of(files.split(",")));
            }

            runner = new StructuralMetricsRunner(new FilesInput(dir, filesToAnalyse, metrics.split(",")));
        } else if (mode.equalsIgnoreCase(DIFF_MODE)) {
            if (!cl.hasOption(BEFORE_OPT) || !cl.hasOption(AFTER_OPT)) {
                throw new ParseException("Files versions not specified: please, specify using -b and -a options. Exiting.");
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

        String export = cl.getOptionValue(EXPORT_OPT);
        if (export != null) {
            Files.write(Path.of(export), gsonString.getBytes(StandardCharsets.UTF_8));
        } else {
            System.out.println(gsonString);
        }
    }
}
