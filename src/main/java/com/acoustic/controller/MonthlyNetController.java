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
@RequestMapping("/monthlyNet")
@CrossOrigin
public class MonthlyNetController {

    public static final String DESCRIPTION = "description";
    public static final String VALUE = "value";
    private final MonthlyNetRepository monthlyNetRepository;
    private final SalaryCalculatorService salaryCalculatorService;



    @PostMapping("/getMonthlyNet/{grossMonthlySalary}")
    public Map<String, String> calculateMonthlyNet(@PathVariable @Min(2000) BigDecimal grossMonthlySalary) {
        var monthlyNetSalary = this.salaryCalculatorService.apply(grossMonthlySalary);
        this.monthlyNetRepository.save(MonthlyNet.builder().monthlyNet(monthlyNetSalary).build());
        return Map.of(DESCRIPTION,this.salaryCalculatorService.getDescription(), VALUE, String.valueOf(monthlyNetSalary));
    }


}
