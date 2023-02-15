package com.wilson.sumawilsontenpo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class UserDataRedis implements Serializable {

    private Long id;
    private String clientUuid;
    private String action;
    private double value;
    private boolean status;
    private int responseCode;
    private String responseDescription;
    private Timestamp startDate;
    private Timestamp localDate;
    private String expiration;

}
