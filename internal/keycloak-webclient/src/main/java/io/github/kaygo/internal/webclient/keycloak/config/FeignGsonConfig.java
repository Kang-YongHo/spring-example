package io.github.kaygo.internal.webclient.keycloak.config;

import feign.Client;
import feign.Logger;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

public class FeignGsonConfig {

    @Bean
    public FeignFormatterRegistrar localDateFeignFormatterRegister() {
        return registry -> {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setUseIsoFormat(true);
            registrar.registerFormatters(registry);
        };
    }

    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(1000, 2000, 3);
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Client feignClient(){
        return new OkHttpClient();
    }

    @Bean
    public Encoder feignEncoder(){
        return new GsonEncoder();
    }

    @Bean
    public Decoder feignDecoder(){
        return new ResponseEntityDecoder(new GsonDecoder());
    }
}