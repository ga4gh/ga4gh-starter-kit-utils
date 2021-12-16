package org.ga4gh.starterkit.utils.lib.apis;

public class ApiMigrationSignature {

    private String api;
    private String version;

    public ApiMigrationSignature(String apiMigrationSignature) {
        String[] sig = apiMigrationSignature.split("@");
        api = sig[0];
        version = sig[1];
    }

    public String getApi() {
        return api;
    }

    public String getVersion() {
        return version;
    }
}
