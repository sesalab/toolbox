package it.sesalab.computemetrics.mining;

import it.sesalab.computemetrics.beans.ClassBean;
import it.sesalab.computemetrics.beans.PackageBean;
import it.sesalab.computemetrics.core.CKMetrics;
import it.sesalab.computemetrics.core.FileUtility;
import it.sesalab.computemetrics.parser.ClassParser;
import it.sesalab.computemetrics.parser.CodeParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.RepositoryFile;
import org.repodriller.scm.SCMRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class Process implements CommitVisitor {
    String output = "commit_hash,mean_loc,median_loc,mean_wmc,median_wmc\n";
    protected List<String> classesToConsider = new ArrayList<>();

    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
        CodeParser codeParser = new CodeParser();

        //int[] LOCs = new int[commit.getModifications().size()];
        List<Integer> LOCs = new ArrayList<>();
        double meanLOC = 0.0;
        double medianLOC = 0.0;

        //int[] WMCs = new int[commit.getModifications().size()];
        List<Integer> WMCs = new ArrayList<>();
        double meanWMC = 0.0;
        double medianWMC = 0.0;

        double numberOfJavaFiles = 0.0;

        //int i = 0;
        //   for (Modification modification : commit.getModifications()) {
        //       String fileName = modification.getFileName();
        //     if(classesToConsider.contains(fileName)) {
        List<RepositoryFile> files = repo.getScm().files();

        for (RepositoryFile file : files) {
            if (file.getFullName().contains(".java")) {
                for (String fileName : classesToConsider) {
                    if (file.getFullName().contains(fileName)) {
                        numberOfJavaFiles++;
                        String sourceCode = file.getSourceCode();

                        CompilationUnit parsed;
                        try {
                            parsed = codeParser.createParser(FileUtility.readFile(file.getFile().getAbsolutePath()));
                            System.out.println("TYPES: " + parsed.types().size() + "   " + file.getFile().getAbsolutePath());
                            TypeDeclaration typeDeclaration = (TypeDeclaration) parsed.types().get(0);

                            Vector<String> imports = new Vector<String>();

                            for (Object importedResource : parsed.imports())
                                imports.add(importedResource.toString());

                            PackageBean packageBean = new PackageBean();
                            packageBean.setName(parsed.getPackage().getName().getFullyQualifiedName());

                            ClassBean classBean = ClassParser.parse(typeDeclaration, packageBean.getName(), imports);
                            classBean.setPathToClass(file.getFile().getAbsolutePath());

                            int loc = CKMetrics.getLOC(classBean);
                            int wmc = CKMetrics.getMcCabeMetric(classBean);

                            if (loc > 0) {
                                LOCs.add(loc);
                                WMCs.add(wmc);
                                //i++;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        //   }
        // }

        // finding medians
        //Arrays.sort(LOCs);
        List<Integer> sortedLOCList = LOCs.stream().sorted().collect(Collectors.toList());
        if (sortedLOCList.size() % 2 == 0)
            medianLOC = ((double) (sortedLOCList.get(sortedLOCList.size() / 2) + sortedLOCList.get(sortedLOCList.size() / 2 - 1)) / 2);
        else
            medianLOC = ((double) sortedLOCList.get(sortedLOCList.size() / 2));

        List<Integer> sortedWMCList = WMCs.stream().sorted().collect(Collectors.toList());
        if (sortedWMCList.size() % 2 == 0)
            medianWMC = ((double) (sortedWMCList.get(sortedWMCList.size() / 2) + sortedWMCList.get(sortedWMCList.size() / 2 - 1)) / 2);
        else
            medianWMC = ((double) sortedWMCList.get(sortedWMCList.size() / 2));

        // finding means
        for (Integer value : LOCs) {
            meanLOC += value;
        }

        for (Integer value : WMCs) {
            meanWMC += value;
        }

        meanLOC = meanLOC / numberOfJavaFiles;
        meanWMC = meanWMC / numberOfJavaFiles;

        output += commit.getHash() + "," + meanLOC + "," + medianLOC + "," + meanWMC + "," + medianWMC + "\n";
        FileUtility.writeFile(output, Computation.outputPath);

        /* writer.write(
                commit.getHash(),
                meanLOC,
                medianLOC,
                meanWMC,
                medianWMC
        ); */
    }

    public void setRefactoringResources() {

    }

}
