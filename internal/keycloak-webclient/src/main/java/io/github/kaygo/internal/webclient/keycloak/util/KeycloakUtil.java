package io.github.kaygo.internal.webclient.keycloak.util;

import org.keycloak.KeycloakPrincipal;

public class KeycloakUtil {

    public static String getId(KeycloakPrincipal principal) {
        return principal.getKeycloakSecurityContext().getToken().getSubject();
    }

    public static Object getAttribute(KeycloakPrincipal principal, String key, Object defaultValue) {
        return principal.getKeycloakSecurityContext().getToken().getOtherClaims().getOrDefault(key, defaultValue);
    }
}
