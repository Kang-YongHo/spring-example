package io.github.kaygo.internal.webclient.keycloak;

import io.github.kaygo.internal.webclient.keycloak.config.KeycloakTokenClientConfig;
import io.github.kaygo.internal.webclient.keycloak.request.AuthTokenRequest;
import io.github.kaygo.internal.webclient.keycloak.request.RefreshTokenRequest;
import io.github.kaygo.internal.webclient.keycloak.response.AccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "keycloak-client", url = "${keycloak.auth-server-url}", configuration = KeycloakTokenClientConfig.class)
public interface KeycloakTokenClient {

    @PostMapping(path = "/realms/{realm}/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AccessTokenResponse> getTokenFromBasicAuth(@PathVariable("realm") String realm,
                                                              @RequestHeader("Authorization") String Authorization,
                                                              @RequestBody AuthTokenRequest body);

    @PostMapping(path = "/realms/{realm}/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AccessTokenResponse> getToken(@PathVariable("realm") String realm,
                                                 @RequestBody AuthTokenRequest body);


    @PostMapping(path = "/realms/{realm}/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AccessTokenResponse> getTokenByRefreshToken(@PathVariable("realm") String realm,
                                                               @RequestBody RefreshTokenRequest body);
}
