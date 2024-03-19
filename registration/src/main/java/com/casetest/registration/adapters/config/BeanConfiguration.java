package com.casetest.registration.adapters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.casetest.registration.adapters.out.persistence.CustomerPersistenceAdapter;
import com.casetest.registration.adapters.out.persistence.jdbc.h2.CustomerJdbcRepository;
import com.casetest.registration.application.port.out.LoadCustomerPort;
import com.casetest.registration.application.service.CustomerService;

@Configuration
public class BeanConfiguration {
    
    @Bean
    CustomerPersistenceAdapter customerAdapter(CustomerJdbcRepository customerJdbcRepository) {
        return new CustomerPersistenceAdapter(customerJdbcRepository);
    }

    @Bean
    CustomerService custonerService(LoadCustomerPort loadCustomerPort) {
        return new CustomerService(loadCustomerPort);
    }

}
