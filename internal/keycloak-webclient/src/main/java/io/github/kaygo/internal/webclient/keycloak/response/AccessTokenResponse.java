package io.github.kaygo.internal.webclient.keycloak.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AccessTokenResponse {
    @SerializedName("access_token")
    protected String token;
    @SerializedName("expires_in")
    protected long expiresIn;
    protected LocalDateTime expiresInDate;
    @SerializedName("refresh_expires_in")
    protected long refreshExpiresIn;
    @SerializedName("refresh_token")
    protected String refreshToken;
    @SerializedName("token_type")
    protected String tokenType;
    @SerializedName("id_token")
    protected String idToken;
    @SerializedName("not-before-policy")
    protected int notBeforePolicy;
    @SerializedName("session_state")
    protected String sessionState;
    protected Map<String, Object> otherClaims = new HashMap();
    @SerializedName("scope")
    protected String scope;
    @SerializedName("error")
    protected String error;
    @SerializedName("error_description")
    protected String errorDescription;
    @SerializedName("error_uri")
    protected String errorUri;

    public AccessTokenResponse() {
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getRefreshExpiresIn() {
        return this.refreshExpiresIn;
    }

    public void setRefreshExpiresIn(long refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getIdToken() {
        return this.idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public int getNotBeforePolicy() {
        return this.notBeforePolicy;
    }

    public void setNotBeforePolicy(int notBeforePolicy) {
        this.notBeforePolicy = notBeforePolicy;
    }

    public String getSessionState() {
        return this.sessionState;
    }

    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

    @JsonAnyGetter
    public Map<String, Object> getOtherClaims() {
        return this.otherClaims;
    }

    @JsonAnySetter
    public void setOtherClaims(String name, Object value) {
        this.otherClaims.put(name, value);
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorUri() {
        return this.errorUri;
    }

    public void setErrorUri(String errorUri) {
        this.errorUri = errorUri;
    }

    public LocalDateTime getExpiresInDate() {
        return expiresInDate;
    }

    public void setExpiresInDate(LocalDateTime expiresInDate) {
        this.expiresInDate = expiresInDate;
    }

    @Override
    public String toString() {
        return "AccessTokenResultVO{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshExpiresIn=" + refreshExpiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", idToken='" + idToken + '\'' +
                ", notBeforePolicy=" + notBeforePolicy +
                ", sessionState='" + sessionState + '\'' +
                ", otherClaims=" + otherClaims +
                ", scope='" + scope + '\'' +
                ", error='" + error + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                ", errorUri='" + errorUri + '\'' +
                '}';
    }
}
