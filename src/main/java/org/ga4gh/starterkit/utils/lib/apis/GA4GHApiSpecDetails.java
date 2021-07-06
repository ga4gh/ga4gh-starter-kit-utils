package org.ga4gh.starterkit.utils.lib.apis;

import java.net.URI;

public class GA4GHApiSpecDetails {

    private String signature;
    private String fullName;
    private URI specGithubRepo;
    private URI starterKitGithubRepo;

    public GA4GHApiSpecDetails() {

    }

    public GA4GHApiSpecDetails(String signature, String fullName, URI specGithubRepo, URI starterKitGithubRepo) {
        this.signature = signature;
        this.fullName = fullName;
        this.specGithubRepo = specGithubRepo;
        this.starterKitGithubRepo = starterKitGithubRepo;
    }
}
