package com.acoustic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class MonthlyNetServiceTest {

    public static final String MONTHLY_NET_DESCRIPTION = "Monthly net";
    @InjectMocks
    private MonthlyNetService monthlyNetService;



    @Test
    void getDescription() {
        assertThat(this.monthlyNetService.getDescription()).isEqualTo(MONTHLY_NET_DESCRIPTION);
    }

    @ParameterizedTest
    @CsvSource({"4319.44,4319.44", "5039.35, 5039.35", "6158.81,6158.81", "10188.77,10188.77"})
    public void getMonthlyNet(BigDecimal input, BigDecimal expected) {
        assertThat(this.monthlyNetService.apply(input)).isEqualTo(expected);
    }
}

