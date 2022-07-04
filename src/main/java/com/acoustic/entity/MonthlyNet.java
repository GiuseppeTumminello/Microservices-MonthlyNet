package com.acoustic.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MonthlyNet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private BigDecimal monthlyNet;

}
