package com.wilson.sumawilsontenpo.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wilson.sumawilsontenpo.entity.UserEntity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class BaseUserResponse {

    private Integer responseCode;
    private String responseDescription;
    private List<UserEntity> responseContent;

}
