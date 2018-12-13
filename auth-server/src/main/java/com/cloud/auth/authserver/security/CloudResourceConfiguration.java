package com.cloud.auth.authserver.security;

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
