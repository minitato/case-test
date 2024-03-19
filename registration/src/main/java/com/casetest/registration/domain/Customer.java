package com.casetest.registration.domain;

import java.math.BigDecimal;

public class Customer {
    
    private final CustomerId customerId;

    public Customer(CustomerId customerId) {
        this.customerId = customerId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public static class CustomerId {
        private long id;
        private String name;
        private BigDecimal balance;
        private BigDecimal limit;
        private boolean isActive;

        public CustomerId(Long id, String name, BigDecimal balance, BigDecimal limit, boolean isActive) {
            this.id = id;
            this.name = name;
            this.balance = balance;
            this.limit = limit;
            this.isActive = isActive;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public BigDecimal getLimit() {
            return limit;
        }

        public boolean isActive() {
            return isActive;
        }
    }

}
