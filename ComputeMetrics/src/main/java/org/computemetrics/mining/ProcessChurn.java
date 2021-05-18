package org.computemetrics.mining;

import org.computemetrics.core.FileUtility;
import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import java.util.ArrayList;
import java.util.List;

public class ProcessChurn implements CommitVisitor {
    String output = "commit_hash,lines_added,lines_removed,code_churn\n";
    Process process = null;

    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
        double added = 0.0;
        double removed = 0.0;
        double codeChurn = 0.0;

        List<String> refactoredFiles = new ArrayList<>();

        for (Modification modification : commit.getModifications()) {
            codeChurn += modification.getAdded() + modification.getRemoved();
            added += modification.getAdded();
            removed += modification.getRemoved();

            refactoredFiles.add(modification.getFileName());

        }

        output += commit.getHash() + "," + added + "," + removed + "," + codeChurn + "\n";
        FileUtility.writeFile(output, Computation.outputChurnPath);
        process.classesToConsider = refactoredFiles;
    }
}