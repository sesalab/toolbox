package it.sesalab.computemetrics.cli;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.sesalab.computemetrics.core.ComputeMetrics;
import it.sesalab.computemetrics.core.Input;
import it.sesalab.computemetrics.core.Output;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class SingleFileComputation {
    public static final String DIR = "dir";
    public static final String FILE = "file";
    public static final String METRICS = "metrics";

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(new Option("" + DIR.charAt(0), DIR, true, "Base directory where looking for files to analyze"));
        options.addOption(new Option("" + FILE.charAt(0), FILE, true, "Source code file to analyze"));
        options.addOption(new Option("" + METRICS.charAt(0), METRICS, true, "Source code file to analyze"));

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cl = parser.parse(options, args);
            if (!cl.hasOption(DIR)) {
                exitError("Base directory not specified. Exiting.");
            }
            if (!cl.hasOption(FILE)) {
                exitError("File not specified. Exiting.");
            }
            if (!cl.hasOption(METRICS)) {
                exitError("No metric specified. Exiting.");
            }
            String dir = cl.getOptionValue(DIR);
            String file = cl.getOptionValue(FILE);
            String metrics = cl.getOptionValue(METRICS);

            ComputeMetrics computeMetrics = new ComputeMetrics(new Input(dir, file, metrics.split(",")));
            Output output = computeMetrics.run();
            Map<String, Object> content = new LinkedHashMap<>();
            content.put(DIR, output.getDirectory());
            content.put(FILE, output.getFile());
            content.putAll(output.getMetrics());
            //content.put("metrics", output.getMetrics());

            Gson gson = new Gson();
            Type gsonType = new TypeToken<Map<String, Object>>() {
            }.getType();
            String gsonString = gson.toJson(content, gsonType);
            System.out.println(gsonString);
        } catch (ParseException e) {
            exitError("Invalid options. Exiting.");
        } catch (IOException e) {
            exitError("File not found. Exiting.");
        } catch (RuntimeException e) {
            exitError(e.getMessage());
        }
    }

    private static void exitError(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
