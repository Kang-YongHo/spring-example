package io.github.kaygo.internal.webclient.keycloak.response;

import com.google.gson.annotations.SerializedName;

public class TokenErrorResponse {
    private String error;
    @SerializedName("error_description")
    private String errorDescription;

    public TokenErrorResponse(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
