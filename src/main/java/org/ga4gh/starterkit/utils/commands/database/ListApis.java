package org.ga4gh.starterkit.utils.commands.database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.ga4gh.starterkit.utils.lib.TablePrinter;
import org.ga4gh.starterkit.utils.lib.apis.AllGA4GHApis;
import org.ga4gh.starterkit.utils.lib.apis.GA4GHApiSpecDetails;
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
        List<String[]> table = new ArrayList<>();
        table.add(new String[] {"API Signature", "Name", "Description"});
        for (GA4GHApiSpecDetails details: AllGA4GHApis.getApiList()) {
            table.add(new String[] {details.getSignature(), details.getFullName(), details.getDescription()});
        }

        TablePrinter.printTable(table);
        return 0;
    }
}
