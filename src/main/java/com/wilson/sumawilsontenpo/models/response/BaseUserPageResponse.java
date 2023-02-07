package com.wilson.sumawilsontenpo.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wilson.sumawilsontenpo.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.domain.Page;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class BaseUserPageResponse {

    private Integer responseCode;
    private String responseDescription;
    private Page<UserEntity> responseContent;

}
