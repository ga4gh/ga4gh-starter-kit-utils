package org.ga4gh.starterkit.utils.commands.database;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "list-apis",
    description = "List GA4GH APIs for which it is possible to apply a database migration",
    version = "0.1.0",
    mixinStandardHelpOptions = true
)
public class ListApis implements Callable<Integer> {

    public void main(String[] args) {
        int exitCode = new CommandLine(new ListApis()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("List APIs...");
        return 0;
    }
}
