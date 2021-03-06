package org.ga4gh.starterkit.utils.lib.apis;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllGA4GHApis {

    private static final Map<String, GA4GHApiSpecDetails> apis = new HashMap<>() {{
        put("drs", 
            new GA4GHApiSpecDetails(
                "drs",
                "Data Repository Service",
                "Generic interface to different data repository types",
                "ga4gh",
                "ga4gh-starter-kit-drs"
            )
        );
        put("wes",
            new GA4GHApiSpecDetails(
                "wes",
                "Workflow Execution Service",
                "Submit and monitor workflow run requests",
                "ga4gh",
                "ga4gh-starter-kit-wes"
            )
        );
        put("data-connect",
                new GA4GHApiSpecDetails(
                        "data-connect",
                        "Data Connect API",
                        "Discover and search datasets",
                        "ga4gh",
                        "ga4gh-starter-kit-data-connect"
                )
        );
        put("passport-broker",
                new GA4GHApiSpecDetails(
                        "passport-broker",
                        "Passport Broker",
                        "provides Passport jwt token",
                        "ga4gh",
                        "ga4gh-starter-kit-passport-broker"
                )
        );
    }};

    private static final List<String> apiKeys = new ArrayList<>() {{
        add("drs");
        add("wes");
        add("data-connect");
        add("passport-broker");
    }};

    public static Map<String, GA4GHApiSpecDetails> getApis() {
        return apis;
    }

    public static List<GA4GHApiSpecDetails> getApiList() {
        List<GA4GHApiSpecDetails> apiList = new ArrayList<>();
        for (String apiKey: apiKeys) {
            apiList.add(getApis().get(apiKey));
        }
        return apiList;
    }
}
