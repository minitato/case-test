
package com.casetest.registration.application.port.in;

import com.casetest.registration.domain.Customer;

/**
 * FindCustomerUseCase
 */
public interface FindCustomerUseCase {

    Customer findCustomer(String name);
    
}