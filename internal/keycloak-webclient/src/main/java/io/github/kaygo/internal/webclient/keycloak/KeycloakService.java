package io.github.kaygo.internal.webclient.keycloak;

import io.github.kaygo.internal.webclient.keycloak.exception.KeycloakExceptionMessages;
import io.github.kaygo.internal.webclient.keycloak.exception.KeycloakTokenException;
import io.github.kaygo.internal.webclient.keycloak.request.AuthTokenRequest;
import io.github.kaygo.internal.webclient.keycloak.request.RefreshTokenRequest;
import io.github.kaygo.internal.webclient.keycloak.response.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class KeycloakService {
    private static final Logger log = LoggerFactory.getLogger(KeycloakService.class);

    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    private final String grantType = "password";

    // TODO 동시성 문제 해결 필요
    private static AccessTokenResponse adminAccessToken;

    private final KeycloakTokenClient keycloakTokenClient;
    private final KeycloakRestApiClient keycloakRestApiClient;

    public KeycloakService(KeycloakTokenClient keycloakTokenClient, KeycloakRestApiClient keycloakRestApiClient) {
        this.keycloakTokenClient = keycloakTokenClient;
        this.keycloakRestApiClient = keycloakRestApiClient;
    }

    private String getRestApiAccessToken() {
        AuthTokenRequest body = new AuthTokenRequest.Builder()
                .grantType("client_credentials")
                .scope("roles")
                .createAuthTokenRequest();
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));

        LocalDateTime nowBeforeRequestSend = LocalDateTime.now();

        ResponseEntity<AccessTokenResponse> response = keycloakTokenClient.getTokenFromBasicAuth(realm, basicAuth, body);

        adminAccessToken = response.getBody();
        adminAccessToken.setExpiresInDate(nowBeforeRequestSend.plusSeconds(adminAccessToken.getExpiresIn()));
        log.info("Keycloak Admin Login Success : {}", adminAccessToken);

        return adminAccessToken.getToken();
    }

    private String getRestApiAccessTokenString() {
        String token;
        LocalDateTime now = LocalDateTime.now();
        if (adminAccessToken != null && now.isBefore(adminAccessToken.getExpiresInDate())) {
            token = "Bearer " + adminAccessToken.getToken();
        } else {
            token = "Bearer " + getRestApiAccessToken();
        }

        return token;
    }


    public ResponseEntity<UserRepresentation> findUser(String username) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        UserRepresentation user = null;

        ResponseEntity<List<UserRepresentation>> response = keycloakRestApiClient.findUsers(getRestApiAccessTokenString(), realm, username);
        if (response.hasBody()) {
            for (UserRepresentation representation : response.getBody()) {
                if (username.equals(representation.getUsername())) {
                    status = HttpStatus.OK;
                    user = representation;
                }
            }
        }

        return ResponseEntity.status(status).body(user);
    }

    public ResponseEntity saveUser(String username, String password) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType("password");
        credential.setValue(password);
        credential.setTemporary(false);

        List<CredentialRepresentation> credentials = Arrays.asList(credential);
        user.setCredentials(credentials);
        user.setEnabled(true);

        ResponseEntity response = keycloakRestApiClient.saveUser(getRestApiAccessTokenString(), realm, user);
        if (HttpStatus.CREATED.value() != response.getStatusCodeValue()) {
            throw new KeycloakTokenException(400, KeycloakExceptionMessages.USER_SAVE_FAIL);
        }
        return response;
    }

    public ResponseEntity saveVendorAdminGroupUserWitnAttribute(String username, String password, Map<String, List<String>> attributes) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setGroups(Collections.singletonList("vendor_admin_group"));
        user.setAttributes(attributes);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType("password");
        credential.setValue(password);
        credential.setTemporary(false);

        List<CredentialRepresentation> credentials = Arrays.asList(credential);
        user.setCredentials(credentials);
        user.setEnabled(true);

        ResponseEntity response = keycloakRestApiClient.saveUser(getRestApiAccessTokenString(), realm, user);
        if (HttpStatus.CREATED.value() != response.getStatusCodeValue()) {
            throw new KeycloakTokenException(400, KeycloakExceptionMessages.USER_SAVE_FAIL);
        }
        return response;
    }

    public ResponseEntity deleteUser(String userId) {
        ResponseEntity response = keycloakRestApiClient.deleteUser(getRestApiAccessTokenString(), realm, userId);
        return response;
    }

    public ResponseEntity updatePasswordByUserId(String userId, String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType("password");
        credential.setValue(password);

        ResponseEntity response = keycloakRestApiClient.updateUserPassword(getRestApiAccessTokenString(), realm, userId, credential);
        if (HttpStatus.NO_CONTENT.value() != response.getStatusCodeValue()) {
            throw new KeycloakTokenException(400, KeycloakExceptionMessages.USER_UPDATE_FAIL);
        }
        return response;
    }

    public ResponseEntity updatePasswordByUsername(String username, String password) {
        String userId = findUser(username).getBody().getId();

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType("password");
        credential.setValue(password);

        ResponseEntity response = keycloakRestApiClient.updateUserPassword(getRestApiAccessTokenString(), realm, userId, credential);
        if (HttpStatus.NO_CONTENT.value() != response.getStatusCodeValue()) {
            throw new KeycloakTokenException(400, KeycloakExceptionMessages.USER_UPDATE_FAIL);
        }
        return response;
    }

    public AccessTokenResponse getAccessToken(String username, String password) {
        AuthTokenRequest requestBody = new AuthTokenRequest.Builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(grantType)
                .username(username)
                .password(password).createAuthTokenRequest();

        ResponseEntity<AccessTokenResponse> response = keycloakTokenClient.getToken(realm, requestBody);
        if (HttpStatus.OK.value() != response.getStatusCodeValue()) {
            throw new KeycloakTokenException(400, KeycloakExceptionMessages.TOKEN_GET_FAIL);
        }
        return response.getBody();
    }

    public AccessTokenResponse getAccessTokenByRefreshToken(String refreshToken) {
        RefreshTokenRequest vo = new RefreshTokenRequest.Builder()
                .refreshToken(refreshToken)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType("refresh_token")
                .createRefreshTokenVo();

        ResponseEntity<AccessTokenResponse> response = keycloakTokenClient.getTokenByRefreshToken(realm, vo);
        if (HttpStatus.OK.value() != response.getStatusCodeValue()) {
            throw new KeycloakTokenException(400, KeycloakExceptionMessages.TOKEN_REFRESH_FAIL);
        }
        return response.getBody();
    }
}