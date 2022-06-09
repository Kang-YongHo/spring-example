package io.github.kaygo.internal.webclient.keycloak.exception;

import io.github.kyago.web.core.exception.BaseExceptionMessage;

public enum KeycloakExceptionMessages implements BaseExceptionMessage {
    LOGIN_FAIL_INVALID_REQUEST("아이디 또는 비밀번호가 잘못되었습니다"),
    LOGIN_FAIL_INVALID_GRANT("접근 권한이 없습니다"),
    LOGIN_FAIL_ACCOUNT_DISABLED("계정이 비활성화 되었습니다"),
    ERROR("서버 내부 에러가 발생했습니다"),

    USER_SAVE_FAIL("계정 생성에 실패했습니다"),
    USER_UPDATE_FAIL("계정 정보 수정에 실패했습니다"),
    TOKEN_GET_FAIL("인증 토큰 발급에 실패했습니다"),
    TOKEN_REFRESH_FAIL("유효하지 않은 갱신 토큰입니다")
    ;
    private String message;

    KeycloakExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
