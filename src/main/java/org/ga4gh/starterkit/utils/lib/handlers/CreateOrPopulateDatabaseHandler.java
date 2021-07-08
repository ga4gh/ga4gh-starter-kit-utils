package org.ga4gh.starterkit.utils.lib.handlers;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map;
import org.ga4gh.starterkit.utils.lib.ChangesetSource;
import org.ga4gh.starterkit.utils.lib.apis.AllGA4GHApis;
import org.ga4gh.starterkit.utils.lib.apis.ApiMigrationSignature;
import org.ga4gh.starterkit.utils.lib.apis.GA4GHApiSpecDetails;

public class CreateOrPopulateDatabaseHandler {

    private static final String createTablesFilename = "create-tables.sql";
    private static final String addTestDatasetFilename = "add-dev-dataset.sql";

    public static Integer createTables(String changeset, String dbURL, String username, String password) {
        return createOrPopulateDatabase(changeset, dbURL, username, password, createTablesFilename);
    }

    public static Integer addTestDataset(String changeset, String dbURL, String username, String password) {
        return createOrPopulateDatabase(changeset, dbURL, username, password, addTestDatasetFilename);
    }

    private static Integer createOrPopulateDatabase(String changeset, String dbURL, String username, String password, String sqlFilename) {
        int exitCode = 0;
        String sqlCommands = null;

        try {
            switch (determineChangesetSource(changeset)) {
                case SIGNATURE:
                    String sqlContentURL = constructSQLContentURLFromSignature(changeset, dbURL, sqlFilename);
                    sqlCommands = getSQLCommandsFromURL(changeset, sqlContentURL);
                    break;
                case URL:
                    sqlCommands = getSQLCommandsFromURL(changeset, changeset);
                    break;
                case FILE:
                    break;
            }

            runSqlUpdate(dbURL, username, password, sqlCommands);
        } catch (Exception ex) {
            exitCode = 1;
            System.out.println(ex.getMessage());
        }
    
        return exitCode;
    }

    private static ChangesetSource determineChangesetSource(String changeset) {

        if (changeset.split("@").length == 2) {
            return ChangesetSource.SIGNATURE;
        }

        try {
            URL url = new URL(changeset);
            url.toURI();
            return ChangesetSource.URL;
        } catch (Exception e) {
            return ChangesetSource.FILE;
        }
    }

    private static String constructSQLContentURLFromSignature(String changeset, String dbURL, String sqlFilename) throws Exception {
        ApiMigrationSignature signature = new ApiMigrationSignature(changeset);
        Map<String, GA4GHApiSpecDetails> allApis = AllGA4GHApis.getApis();
        
        if (!allApis.containsKey(signature.getApi())) {
            throw new Exception("'" + signature.getApi() + "' is not a valid GA4GH API");
        }

        GA4GHApiSpecDetails apiDetails = allApis.get(signature.getApi());
        String databaseType = determineDatabaseTypeFromURL(dbURL);

        return "https://raw.githubusercontent.com/"
            + apiDetails.getStarterKitGithubOrg() + "/"
            + apiDetails.getStarterKitGithubRepoName() + "/"
            + signature.getVersion() + "/"
            + "database/"
            + databaseType + "/"
            + sqlFilename;
    }

    private static String determineDatabaseTypeFromURL(String dbURL) throws Exception {

        if (dbURL.startsWith("jdbc:sqlite")) {
            return "sqlite";
        }

        if (dbURL.startsWith("jdbc:postgresql")) {
            return "postgresql";
        }
        
        throw new IllegalArgumentException("Invalid JDBC URL: MUST be a valid 'sqlite' or 'postgresql' JDBC URL");
    }

    private static String getSQLCommandsFromURL(String changeset, String url) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();
        
        HttpResponse response = client.send(request, BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception("Nothing found at " + changeset);
        }

        return response.body().toString();
    }

    private static void runSqlUpdate(String dbURL, String username, String password, String sqlCommands) throws Exception {
        Connection connection = null;
        if (username.equals("") && password.equals("")) {
            connection = DriverManager.getConnection(dbURL);
        } else {
            connection = DriverManager.getConnection(dbURL, username, password);
        }
        
        
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sqlCommands);
    }
}
