package com.acoustic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class MonthlyNetService implements SalaryCalculatorService {

    public static final int MONTHS_NUMBER = 12;


    @Override
    public BigDecimal apply(BigDecimal grossMonthlySalary) {
        return grossMonthlySalary.setScale(2, RoundingMode.HALF_EVEN);
    }


    @Override
    public String getDescription() {
        return "Monthly net";
    }


}
