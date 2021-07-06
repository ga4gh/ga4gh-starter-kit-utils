package org.ga4gh.starterkit.utils.commands.database;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "database",
    description = "Perform operations on databases backing starter kit services",
    version = "0.1.0",
    mixinStandardHelpOptions = true,
    subcommands = {
        ListApis.class,
        ApplyMigration.class
    }
)
public class Database implements Callable<Integer> {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Database()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        String msg = new CommandLine(new Database()).getHelp().abbreviatedSynopsis() + "\n"
            + "No subcommand specified, use '-h' to view subcommands";
        System.out.println(msg);
        return 0;
    }
}
