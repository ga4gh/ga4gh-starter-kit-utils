package org.ga4gh.starterkit.utils.lib.apis;

import java.net.URI;

public class GA4GHApiSpecDetails {

    private String signature;
    private String fullName;
    private String description;
    private String starterKitGithubOrg;
    private String starterKitGithubRepoName;

    public GA4GHApiSpecDetails() {

    }

    public GA4GHApiSpecDetails(String signature, String fullName, String description, String starterKitGithubOrg, String starterKitGithubRepoName) {
        this.signature = signature;
        this.fullName = fullName;
        this.description = description;
        this.starterKitGithubOrg = starterKitGithubOrg;
        this.starterKitGithubRepoName = starterKitGithubRepoName;
    }

    public String getSignature() {
        return signature;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getStarterKitGithubOrg() {
        return starterKitGithubOrg;
    }

    public String getStarterKitGithubRepoName() {
        return starterKitGithubRepoName;
    }
}
