package com.casetest.registration.adapters.out.persistence;

import java.util.Optional;

import com.casetest.registration.adapters.out.persistence.jdbc.h2.CustomerJdbcRepository;
import com.casetest.registration.adapters.out.persistence.jdbc.h2.entity.CustomerJdbcEntity;
import com.casetest.registration.application.port.out.LoadCustomerPort;
import com.casetest.registration.domain.Customer.CustomerId;
import com.casetest.registration.domain.exception.CustomerNotFoundException;

public class CustomerPersistenceAdapter implements LoadCustomerPort {

    private final CustomerJdbcRepository customerJdbcRepository;

    public CustomerPersistenceAdapter(CustomerJdbcRepository customerJdbcRepository) {
        this.customerJdbcRepository = customerJdbcRepository;
    }

    @Override
    public CustomerId loadCustomer(String name) {
        CustomerJdbcEntity customer = Optional
            .ofNullable(customerJdbcRepository.findByName(name))
            .orElseThrow(CustomerNotFoundException::new);
        return new CustomerId(
            customer.getId(),
            customer.getName(), 
            customer.getBalance(), 
            customer.getLimit(), 
            customer.isActive());           
    }
    
}