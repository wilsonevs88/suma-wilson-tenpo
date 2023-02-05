package com.wilson.sumawilsontenpo.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class UserResponse {

    private Long id;
    String action;
    private String clientUuid;
    private double value;
    private int responseCode;
    private String responseDescription;
    Timestamp startDate;
    Timestamp localDate;

}
