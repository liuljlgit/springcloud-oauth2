package com.cloud.zuul.zuulserver.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oauth2", ignoreUnknownFields = false)
public class OAuth2Properties {
    private WebClientConfiguration webClientConfiguration = new WebClientConfiguration();

    private SignatureVerification signatureVerification = new SignatureVerification();

    private ClientAuthorization clientAuthorization = new ClientAuthorization();

    public WebClientConfiguration getWebClientConfiguration() {
        return webClientConfiguration;
    }

    public SignatureVerification getSignatureVerification() {
        return signatureVerification;
    }

    public ClientAuthorization getClientAuthorization() {
        return clientAuthorization;
    }

    public static class WebClientConfiguration {
        private String clientId = "gateway_client";
        private String secret = "123456";
        /**
         * Holds the session timeout in seconds for non-remember-me sessions.
         * After so many seconds of inactivity, the session will be terminated.
         * Only checked during token refresh, so long access token validity may
         * delay the session timeout accordingly.
         */
        private int sessionTimeoutInSeconds = 1800;
        /**
         * Defines the cookie domain. If specified, cookies will be set on this domain.
         * If not configured, then cookies will be set on the top-level domain of the
         * request you sent, i.e. if you send a request to <code>app1.your-domain.com</code>,
         * then cookies will be set <code>on .your-domain.com</code>, such that they
         * are also valid for <code>app2.your-domain.com</code>.
         */
        private String cookieDomain;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public int getSessionTimeoutInSeconds() {
            return sessionTimeoutInSeconds;
        }

        public void setSessionTimeoutInSeconds(int sessionTimeoutInSeconds) {
            this.sessionTimeoutInSeconds = sessionTimeoutInSeconds;
        }

        public String getCookieDomain() {
            return cookieDomain;
        }

        public void setCookieDomain(String cookieDomain) {
            this.cookieDomain = cookieDomain;
        }
    }

    public static class SignatureVerification {
        /**
         * Maximum refresh rate for public keys in ms.
         * We won't fetch new public keys any faster than that to avoid spamming UAA in case
         * we receive a lot of "illegal" tokens.
         */
        private long publicKeyRefreshRateLimit = 10 * 1000L;
        /**
         * Maximum TTL for the public key in ms.
         * The public key will be fetched again from UAA if it gets older than that.
         * That way, we make sure that we get the newest keys always in case they are updated there.
         */
        private long ttl = 24 * 60 * 60 * 1000L;
        /**
         * Endpoint where to retrieve the public key used to verify token signatures.
         */
        private String publicKeyEndpointUri = "http://uaa/oauth/token_key";

        public long getPublicKeyRefreshRateLimit() {
            return publicKeyRefreshRateLimit;
        }

        public void setPublicKeyRefreshRateLimit(long publicKeyRefreshRateLimit) {
            this.publicKeyRefreshRateLimit = publicKeyRefreshRateLimit;
        }

        public long getTtl() {
            return ttl;
        }

        public void setTtl(long ttl) {
            this.ttl = ttl;
        }

        public String getPublicKeyEndpointUri() {
            return publicKeyEndpointUri;
        }

        public void setPublicKeyEndpointUri(String publicKeyEndpointUri) {
            this.publicKeyEndpointUri = publicKeyEndpointUri;
        }
    }

    public static class ClientAuthorization {
        private String accessTokenUri = "http://auth-server/oauth/token";
        private String tokenServiceId = "auth-server";
        private String clientId = "";
        private String clientSecret = "";

        public String getAccessTokenUri() {
            return accessTokenUri;
        }

        public void setAccessTokenUri(String accessTokenUri) {
            this.accessTokenUri = accessTokenUri;
        }

        public String getTokenServiceId() {
            return tokenServiceId;
        }

        public void setTokenServiceId(String tokenServiceId) {
            this.tokenServiceId = tokenServiceId;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }
}
