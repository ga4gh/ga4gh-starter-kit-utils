package org.ga4gh.starterkit.utils.commands.database;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.ga4gh.starterkit.utils.lib.TablePrinter;
import org.ga4gh.starterkit.utils.lib.apis.AllGA4GHApis;
import org.ga4gh.starterkit.utils.lib.apis.GA4GHApiSpecDetails;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(
    name = "list-migrations",
    description = "View all available database migrations for a given GA4GH API",
    version = "0.1.0",
    mixinStandardHelpOptions = true
)
public class ListMigrations implements Callable<Integer> {

    @Parameters(
        paramLabel = "API_SIGNATURE",
        description = "GA4GH API name (use 'list-apis' to view full list)"
    )
    private String apiSignature;

    public void main(String[] args) {
        int exitCode = new CommandLine(new ListMigrations()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        int exitCode = 0;

        try {
            Map<String, GA4GHApiSpecDetails> allApis = AllGA4GHApis.getApis();
            if (!allApis.containsKey(apiSignature)) {
                throw new Exception("'" + apiSignature + "' is not a valid GA4GH API");
            }

            TablePrinter.printTable(
                getReleaseSignatures(
                    sendGithubApiReleasesRequest(allApis.get(apiSignature))
                )
            );
        } catch (Exception ex) {
            exitCode = 1;
            System.out.println(ex.getMessage());
        }
        return exitCode;
    }

    private String sendGithubApiReleasesRequest(GA4GHApiSpecDetails api) throws IOException, InterruptedException {
        String url = "https://api.github.com/repos/"
            + api.getStarterKitGithubOrg() + "/"
            + api.getStarterKitGithubRepoName() 
            + "/releases";
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();
        
        HttpResponse response = client.send(request, BodyHandlers.ofString());
        return response.body().toString();
    }

    private List<String[]> getReleaseSignatures(String responseBody) throws JsonProcessingException {
        List<String[]> releaseSignatures = new ArrayList<>() {{
            add(new String[] {"API Migration Signatures: " + apiSignature});
        }};

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode json = (ArrayNode) mapper.readTree(responseBody);

        for (int i = 0; i < json.size(); i++) {
            JsonNode item = json.get(i);
            String tag = item.get("tag_name").textValue();
            String releaseSignature = apiSignature + "@" + tag;
            releaseSignatures.add(new String[]{releaseSignature});
        }

        return releaseSignatures;
    }
}
