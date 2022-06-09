package io.github.kaygo.internal.webclient.keycloak.exception;


import io.github.kyago.web.core.exception.BaseException;
import io.github.kyago.web.core.exception.BaseExceptionMessage;

public class KeycloakTokenException extends BaseException {
    int code;

    public KeycloakTokenException(int code, String message) {
        super(message);
        this.code = code;
    }

    public KeycloakTokenException(int code, BaseExceptionMessage message) {
        super(message);
        this.code = code;
    }
}
