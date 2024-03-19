package com.casetest.registration.adapters.in.rest;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.casetest.registration.application.port.in.FindCustomerUseCase;
import com.casetest.registration.domain.Customer;

@RestController
@RequestMapping("/registration")
public class CustomerController {

    private final FindCustomerUseCase findCustomerUseCase;

    public CustomerController(FindCustomerUseCase findCustomerUseCase) {
        this.findCustomerUseCase = findCustomerUseCase;
    }

    @ResponseBody
    @GetMapping("/customer/{name}")
    public CustomerResponse get(@PathVariable("name") String value) {
        Customer customer = findCustomerUseCase.findCustomer(value);
        return new CustomerResponse(customer);
    }
    
}

class CustomerResponse {
    
    Long id;
    String name;
    BigDecimal limit;
    boolean active;

    public CustomerResponse(Customer customer) {
        this.id = customer.getCustomerId().getId();
        this.name = customer.getCustomerId().getName();
        this.limit = customer.getCustomerId().getLimit();
        this.active = customer.getCustomerId().isActive();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public boolean isActive() {
        return active;
    }
    
}
