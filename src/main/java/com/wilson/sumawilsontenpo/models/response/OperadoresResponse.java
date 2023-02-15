package com.wilson.sumawilsontenpo.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class OperadoresResponse {

    private String clientUuid;
    private double value;
    private boolean status;
    private String expiration;

}
