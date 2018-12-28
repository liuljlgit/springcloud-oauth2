package com.cloud.auth.authserver.security.resource;

import java.util.List;

public class CloudResourceConfiguration {
    private List<String> antMatchersList;

    public CloudResourceConfiguration() {
    }

    public List<String> getAntMatchersList() {
        return this.antMatchersList;
    }

    public void setAntMatchersList(List<String> antMatchersList) {
        this.antMatchersList = antMatchersList;
    }
}
