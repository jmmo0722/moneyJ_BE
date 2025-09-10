package com.project.moneyj.codef.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenResponseDTO {

    @JsonProperty("access_token") private String accessToken;
    @JsonProperty("token_type")   private String tokenType;   // e.g. "Bearer"
    @JsonProperty("expires_in")   private Long   expiresIn;   // seconds
    private String scope;
}
