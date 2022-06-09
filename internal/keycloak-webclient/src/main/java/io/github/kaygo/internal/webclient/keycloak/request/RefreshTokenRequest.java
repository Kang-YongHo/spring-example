package io.github.kaygo.internal.webclient.keycloak.request;


public class RefreshTokenRequest {
    String client_id;
    String client_secret;
    String grant_type;
    String refresh_token;

    public RefreshTokenRequest(String client_id, String client_secret, String grant_type, String refresh_token) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.grant_type = grant_type;
        this.refresh_token = refresh_token;
    }

    public static class Builder {
        private String clientId;
        private String clientSecret;
        private String grantType;
        private String refreshToken;

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder grantType(String grantType) {
            this.grantType = grantType;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public RefreshTokenRequest createRefreshTokenVo() {
            return new RefreshTokenRequest(clientId, clientSecret, grantType, refreshToken);
        }
    }
}
