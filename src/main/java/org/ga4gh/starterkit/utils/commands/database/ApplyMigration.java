package org.ga4gh.starterkit.utils.commands.database;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(
    name = "apply-migration",
    description = "Apply a database migration to a relational database",
    version = "0.1.0",
    mixinStandardHelpOptions = true
)
public class ApplyMigration implements Callable<Integer> {

    @Parameters(
        paramLabel = "CHANGESET",
        description = "Changeset to apply. Can be a either a valid API migration signature, URL, or path to changeset file.\n"
            + "signature: e.g. drs@1.0.0\n"
            + "url: e.g. https://changesets.somesite.org/drs-changeset.xml\n"
            + "file path: ./data/changesets/drs-changeset.xml\n"
    )
    private String changeset;

    public void main(String[] args) {
        int exitCode = new CommandLine(new ApplyMigration()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Apply migration..." + changeset);
        return 0;
    }
}
