package com.casetest.registration.adapters.out.persistence.jdbc.h2.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class CustomerJdbcEntity {
    
    @Id
    private Long id;
    private String name;
    private BigDecimal balance;
    
    @Column(name = "transfer_limit")
    private BigDecimal limit;

    @Column(name = "is_active")
    private boolean isActive;

    public CustomerJdbcEntity() {
        super();
    }

    public CustomerJdbcEntity(Long id, String name, BigDecimal balance, BigDecimal limit, boolean isActive) {
        super();
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.limit = limit;
        this.isActive = isActive;
    }

    public CustomerJdbcEntity(String name, BigDecimal balance, BigDecimal limit, boolean isActive) {
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
