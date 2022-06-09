package io.github.kaygo.internal.webclient.keycloak.config;

import com.google.gson.Gson;
import feign.Response;
import feign.Util;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.spring.SpringFormEncoder;
import io.github.kaygo.internal.webclient.keycloak.exception.KeycloakTokenException;
import io.github.kaygo.internal.webclient.keycloak.response.TokenErrorResponse;
import io.github.kaygo.internal.webclient.keycloak.exception.KeycloakExceptionMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeycloakTokenClientConfig {
    @Bean
    @ConditionalOnMissingBean
    Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
        return new SpringFormEncoder(new SpringEncoder(converters));
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    public static class CustomErrorDecoder implements ErrorDecoder {
        private static final Logger log = LoggerFactory.getLogger(CustomErrorDecoder.class);
        private final Gson gson = new Gson();

        public KeycloakTokenException decode(String methodKey, Response response) {
            KeycloakTokenException exception = new KeycloakTokenException(response.status(), response.reason());

            try {
                if (response.body() != null) {
                    byte[] body = Util.toByteArray(response.body().asInputStream());
                    String message = getBodyAsString(body, response.headers());
                    log.error("Keycloak exception message : {}", message);
                    TokenErrorResponse tokenError = gson.fromJson(message, TokenErrorResponse.class);
                    String error = tokenError.getError();
                    String errorDesciption = tokenError.getErrorDescription();

                    if("invalid_request".equals(error)){
                        if("Missing parameter: username".equals(errorDesciption) || "Invalid user credentials".equals(errorDesciption)){
                            exception = new KeycloakTokenException(response.status(), KeycloakExceptionMessages.LOGIN_FAIL_INVALID_REQUEST);
                        }
                    } else if ("invalid_grant".equals(error)) {
                        if("Invalid user credentials".equals(errorDesciption)) {
                            exception = new KeycloakTokenException(response.status(), KeycloakExceptionMessages.LOGIN_FAIL_INVALID_REQUEST);
                        } else if ("Account disabled".equals(errorDesciption)) {
                            exception = new KeycloakTokenException(response.status(), KeycloakExceptionMessages.LOGIN_FAIL_ACCOUNT_DISABLED);
                        } else if ("Invalid refresh token".equals(errorDesciption) || "Token is not active".equals(errorDesciption)) {
                            exception = new KeycloakTokenException(response.status(), KeycloakExceptionMessages.TOKEN_REFRESH_FAIL);
                        }
                    }
                }
            } catch (IOException var4) {
            }

            return exception;
        }
    }

    private static String getBodyAsString(byte[] body, Map<String, Collection<String>> headers) {
        Charset charset = getResponseCharset(headers);
        if (charset == null) {
            charset = Util.UTF_8;
        }

        return getResponseBody(body, charset);
    }

    private static String getResponseBody(byte[] body, Charset charset) {
        return body.length < 400 ? new String(body, charset) : getResponseBodyPreview(body, charset);
    }

    private static String getResponseBodyPreview(byte[] body, Charset charset) {
        try {
            Reader reader = new InputStreamReader(new ByteArrayInputStream(body), charset);
            CharBuffer result = CharBuffer.allocate(200);
            reader.read(result);
            reader.close();
            result.flip();
            return result + "... (" + body.length + " bytes)";
        } catch (IOException var4) {
            return var4 + ", failed to parse response";
        }
    }

    private static Charset getResponseCharset(Map<String, Collection<String>> headers) {
        Collection<String> strings = (Collection)headers.get("content-type");
        if (strings != null && strings.size() != 0) {
            Pattern pattern = Pattern.compile("charset=([^\\s])");
            Matcher matcher = pattern.matcher((CharSequence)strings.iterator().next());
            if (!matcher.lookingAt()) {
                return null;
            } else {
                String group = matcher.group(1);
                return !Charset.isSupported(group) ? null : Charset.forName(group);
            }
        } else {
            return null;
        }
    }
}
