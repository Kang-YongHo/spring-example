package io.github.kyago.web.core.exception;

public class BaseException extends RuntimeException {
    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(BaseExceptionMessage message) { super(message.getMessage()); }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}
