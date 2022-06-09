package io.github.kaygo.internal.webclient.keycloak.request;


public class AuthTokenRequest {
    String client_id;
    String client_secret;
    String username;
    String password;
    String grant_type;
    String scope;

    public AuthTokenRequest(String client_id, String client_secret, String username, String password, String grant_type, String scope) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
        this.scope = scope;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "AuthTokenInVO{" +
                "client_id='" + client_id + '\'' +
                ", client_secret='" + client_secret + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", grant_Type='" + grant_type + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }

    public static class Builder{
        private String clientId;
        private String clientSecret;
        private String username;
        private String password;
        private String grantType;
        private String scope;

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder grantType(String grantType) {
            this.grantType = grantType;
            return this;
        }

        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        public AuthTokenRequest createAuthTokenRequest() {
            return new AuthTokenRequest(clientId, clientSecret, username, password, grantType, scope);
        }
    }
}
