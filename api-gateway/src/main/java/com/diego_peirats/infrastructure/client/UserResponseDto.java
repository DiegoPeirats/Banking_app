package com.diego_peirats.infrastructure.client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserResponseDto(String username, String password, String role, boolean enabled, String email) {}