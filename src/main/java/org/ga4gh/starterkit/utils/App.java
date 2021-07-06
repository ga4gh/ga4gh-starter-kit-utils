/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.ga4gh.starterkit.utils;

import java.util.concurrent.Callable;
import org.ga4gh.starterkit.utils.commands.database.Database;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "ga4gh-starter-kit-utils",
    description = "Helper utils for GA4GH Starter Kit microservices",
    version = "0.1.0",
    mixinStandardHelpOptions = true,
    subcommands = {
        Database.class
    }
)
public class App implements Callable<Integer> {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        String msg = new CommandLine(new App()).getHelp().abbreviatedSynopsis() + "\n"
            + "No subcommand specified, use '-h' to view subcommands";
        System.out.println(msg);
        return 0;
    }
}