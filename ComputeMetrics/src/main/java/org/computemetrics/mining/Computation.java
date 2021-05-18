package org.computemetrics.mining;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Computation implements Study {
    static File file;
    static String pathToRepositories;
    static String outputChurnPath;
    static String outputPath;
    static CSVFile output;

    public static void main(String[] args) {
        Computation.file = new File(args[0]);
        Computation.pathToRepositories = args[1];
        Computation.outputChurnPath = args[2];
        Computation.outputPath = args[3];
        Computation.output = new CSVFile("./output.csv");
        new RepoDriller().start(new Computation());
    }

    @Override
    public void execute() {
        Process process = new Process();
        ProcessChurn processChurn = new ProcessChurn();
        processChurn.process = process;

        try (CSVReader reader = new CSVReader(new FileReader(Computation.file))) {
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {

                File check = new File(Computation.pathToRepositories + "/" + lineInArray[1]);
                if (!check.exists()) { // then, the project must be cloned.
                    //System.out.println(lineInArray[1]);
                    Git.cloneRepository()
                            .setURI(lineInArray[1])
                            .setDirectory(check)
                            .call();
                }

                // If the commit is of type 'refactoring', then we compute the code churn only
                if (lineInArray[4].equals("refactoring")) {
                    new RepositoryMining()
                            .in(GitRepository.singleProject(check.getAbsolutePath()))
                            .through(Commits.single(lineInArray[2]))
                            .process(processChurn, Computation.output)
                            .mine();
                }

                // If the commit is of type 'parent', then we compute LOC and WMC on the refactored classes only
                if (lineInArray[4].equals("parent")) {
                    new RepositoryMining()
                            .in(GitRepository.singleProject(check.getAbsolutePath()))
                            .through(Commits.single(lineInArray[2]))
                            .process(process, Computation.output)
                            .mine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        } catch (InvalidRemoteException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        System.out.print("Here we are");

    }
}
