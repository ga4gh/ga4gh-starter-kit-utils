package org.ga4gh.starterkit.utils.commands.database;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "list-migrations",
    description = "View all available database migrations for a given GA4GH API",
    version = "0.1.0",
    mixinStandardHelpOptions = true
)
public class ListMigrations implements Callable<Integer> {

    public void main(String[] args) {
        int exitCode = new CommandLine(new ListMigrations()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("View available migrations...");
        return 0;
    }
}
