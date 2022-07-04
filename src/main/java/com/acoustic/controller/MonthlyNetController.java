package com.acoustic.controller;


import com.acoustic.entity.MonthlyNet;
import com.acoustic.repository.MonthlyNetRepository;
import com.acoustic.service.SalaryCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monthlyNet")
public class MonthlyNetController {

    public static final String TOTAL_ZUS_DESCRIPTION = "Total zus";
    public static final String HEALTH_DESCRIPTION = "Health";
    public static final String TAX_DESCRIPTION = "Tax";
    private static final String TOTAL_ZUS_ENDPOINT = "http://TOTAL-ZUS/totalZus/getTotalZus/";
    private static final String HEALTH_ENDPOINT = "http://HEALTH/health/getHealth/";
    private static final String TAX_ENDPOINT = "http://TAX/tax/getTax/";
    private final MonthlyNetRepository monthlyNetRepository;
    private final SalaryCalculatorService salaryCalculatorService;
    private final RestTemplate restTemplate;


    @PostMapping("/getMonthlyNet/{grossMonthlySalary}")
    public Map<String, BigDecimal> calculateMonthlyNet(@PathVariable @Min(2000) BigDecimal grossMonthlySalary) {
        var tax = this.salaryCalculatorService.apply(grossMonthlySalary.subtract((getTotalZus(grossMonthlySalary).add(getHealth(grossMonthlySalary).add(getTax(grossMonthlySalary))))));
        this.monthlyNetRepository.save(MonthlyNet.builder().monthlyNet(tax).build());
        return new LinkedHashMap<>(Map.of(this.salaryCalculatorService.getDescription(), tax));
    }


    public BigDecimal getTotalZus(BigDecimal grossMonthlySalary) {
        var totalZus = Objects.requireNonNull(this.restTemplate.postForEntity(TOTAL_ZUS_ENDPOINT + grossMonthlySalary, HttpMethod.POST, HashMap.class).getBody()).get(TOTAL_ZUS_DESCRIPTION);
        return BigDecimal.valueOf(((Double) (totalZus)));
    }

    public BigDecimal getHealth(BigDecimal grossMonthlySalary) {
        var health = Objects.requireNonNull(this.restTemplate.postForEntity(HEALTH_ENDPOINT + grossMonthlySalary, HttpMethod.POST, HashMap.class).getBody()).get(HEALTH_DESCRIPTION);
        return BigDecimal.valueOf(((Double) (health)));
    }

    public BigDecimal getTax(BigDecimal grossMonthlySalary) {
        var tax = Objects.requireNonNull(this.restTemplate.postForEntity(TAX_ENDPOINT + grossMonthlySalary, HttpMethod.POST, HashMap.class).getBody()).get(TAX_DESCRIPTION);
        return BigDecimal.valueOf(((Double) (tax)));
    }
}
