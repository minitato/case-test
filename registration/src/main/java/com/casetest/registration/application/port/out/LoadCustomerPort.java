package com.casetest.registration.application.port.out;

import com.casetest.registration.domain.Customer.CustomerId;

public interface LoadCustomerPort {

    CustomerId loadCustomer(String name);
    
} 
