package com.wilson.sumawilsontenpo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String action;
    @Column(name = "client_uuid")
    private String clientUuid;
    Timestamp localDate;
    @Column(name = "response_code")
    private int responseCode;
    @Column(name = "response_description")
    private String responseDescription;
    @Column(name = "start_date")
    Timestamp startDate;
    private double value;
    private boolean state;

}
