package org.ga4gh.starterkit.utils.commands.database;

import java.util.concurrent.Callable;
import org.ga4gh.starterkit.utils.lib.handlers.CreateOrPopulateDatabaseHandler;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import static org.ga4gh.starterkit.common.constant.DatabasePropsConstants.DEFAULT_URL;
import static org.ga4gh.starterkit.common.constant.DatabasePropsConstants.DEFAULT_USERNAME;
import static org.ga4gh.starterkit.common.constant.DatabasePropsConstants.DEFAULT_PASSWORD;

@Command(
    name = "add-test-dataset",
    description = "Populate database tables with test dataset for a particular GA4GH API",
    version = "0.1.1",
    mixinStandardHelpOptions = true
)
public class AddTestDataset implements Callable<Integer> {

    @Parameters(
        paramLabel = "SOURCE",
        description = "Test dataset source to apply. "
            + "Can be a either a valid API migration signature (e.g. drs@1.0.0), "
            + "URL (e.g. https://somesite.com/drs-tables.sql), "
            + "or file path (e.g. ./drs-tables.sql). Use 'list-migrations' "
            + "to view available migrations"
    )
    private String source;

    @Option(
        names = {"-d", "--db-url"},
        paramLabel = "DB_URL",
        description = "Valid jdbc:// database connection URL",
        defaultValue = DEFAULT_URL
    )
    private String dbURL;

    @Option(
        names = {"-u", "--username"},
        description = "Database username",
        defaultValue = DEFAULT_USERNAME
    )
    private String username;

    @Option(
        names = {"-p", "--password"},
        description = "Database password",
        defaultValue = DEFAULT_PASSWORD
    )
    private String password;

    public void main(String[] args) {
        int exitCode = new CommandLine(new CreateTables()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        return CreateOrPopulateDatabaseHandler.addTestDataset(source, dbURL, username, password);
    }
}
