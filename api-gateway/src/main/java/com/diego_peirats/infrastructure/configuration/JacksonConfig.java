package com.diego_peirats.infrastructure.configuration;

import org.springframework.security.core.GrantedAuthority;

import com.diego_peirats.infrastructure.client.response.GrantedAuthorityDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Crea un módulo de Jackson donde puedes registrar el deserializador personalizado
        SimpleModule module = new SimpleModule();
        module.addDeserializer(GrantedAuthority.class, new GrantedAuthorityDeserializer()); // Aquí registramos el deserializador

        // Registra el módulo
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
