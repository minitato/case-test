package com.casetest.account.adapter.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.casetest.account.application.service.MoneyTransferLimit;
import com.casetest.account.application.service.PerDayLimit;
import com.casetest.account.domain.Money;

@Configuration
@EnableConfigurationProperties(InfrastructureSettingProperties.class)
public class CaseTestConfiguration {

    @Bean
    MoneyTransferLimit moneyTransferLimit(InfrastructureSettingProperties properties) {
        return new MoneyTransferLimit(Money.of(properties.getTransferThreshold()));
    }

    @Bean
    PerDayLimit perDayLimit(InfrastructureSettingProperties properties) {
        return new PerDayLimit(Money.of(properties.getLimitPerDay()));
    }

}
