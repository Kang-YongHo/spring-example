package io.github.kaygo.internal.webclient.keycloak;

import io.github.kaygo.internal.webclient.keycloak.config.FeignGsonConfig;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "keycloak-admin-client", url = "${keycloak.auth-server-url}", configuration = FeignGsonConfig.class)
public interface KeycloakRestApiClient {

    @PutMapping(path = "/admin/realms/{realm}/users/{id}/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity updateUserPassword(@RequestHeader("Authorization") String Authorization,
                                      @PathVariable("realm") String realm,
                                      @PathVariable("id") String id,
                                      @RequestBody CredentialRepresentation body);

    @PutMapping(path = "/admin/realms/{realm}/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity updateUsername(@RequestHeader("Authorization") String Authorization,
                                  @PathVariable("realm") String realm,
                                  @PathVariable("id") String id,
                                  @RequestBody UserRepresentation body);

    @PostMapping(path = "/admin/realms/{realm}/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity saveUser(@RequestHeader("Authorization") String Authorization,
                            @PathVariable("realm") String realm,
                            @RequestBody UserRepresentation body);

    @DeleteMapping(path = "/admin/realms/{realm}/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteUser(@RequestHeader("Authorization") String Authorization,
                              @PathVariable("realm") String realm,
                              @PathVariable("id") String id);

    @GetMapping(path = "/admin/realms/{realm}/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserRepresentation>> findUsers(@RequestHeader("Authorization") String Authorization,
                                                       @PathVariable("realm") String realm,
                                                       @RequestParam("username") String username);

}
