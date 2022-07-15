package com.acoustic.controller;


import com.acoustic.entity.MonthlyNet;
import com.acoustic.repository.MonthlyNetRepository;
import com.acoustic.service.SalaryCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monthly-net")
@CrossOrigin
public class MonthlyNetController {

    public static final String DESCRIPTION = "description";
    public static final String VALUE = "value";
    public static final int MINIMUM_GROSS = 2000;
    private final MonthlyNetRepository monthlyNetRepository;
    private final SalaryCalculatorService salaryCalculatorService;



    @PostMapping("/calculation/{grossMonthlySalary}")
    public Map<String, String> calculateMonthlyNet(@PathVariable @Min(MINIMUM_GROSS) BigDecimal grossMonthlySalary) {
        var monthlyNetSalary = this.salaryCalculatorService.apply(grossMonthlySalary);
        this.monthlyNetRepository.save(MonthlyNet.builder().monthlyNetAmount(monthlyNetSalary).build());
        return Map.of(DESCRIPTION,this.salaryCalculatorService.getDescription(), VALUE, String.valueOf(monthlyNetSalary));
    }


}
