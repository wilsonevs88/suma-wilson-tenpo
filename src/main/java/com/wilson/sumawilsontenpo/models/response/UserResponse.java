package com.wilson.sumawilsontenpo.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private Long id;
    private String clientUuid;
    private String action;
    private double value;
    private boolean state;
    private int responseCode;
    private String responseDescription;
    private Timestamp startDate;
    private Timestamp localDate;
    private String expiration;

}
