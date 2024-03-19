package com.casetest.registration.application.service;

import com.casetest.registration.application.port.in.FindCustomerUseCase;
import com.casetest.registration.application.port.out.LoadCustomerPort;
import com.casetest.registration.domain.Customer;

public class CustomerService implements FindCustomerUseCase {

    private final LoadCustomerPort loadCustomerPort;

    public CustomerService(LoadCustomerPort loadCustomerPort) {
        this.loadCustomerPort = loadCustomerPort;
    }

    @Override
    public Customer findCustomer(String name) {
        return new Customer(loadCustomerPort.loadCustomer(name));
    }
    


}
