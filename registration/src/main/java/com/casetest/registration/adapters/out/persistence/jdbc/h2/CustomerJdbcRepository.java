package com.casetest.registration.adapters.out.persistence.jdbc.h2;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.casetest.registration.adapters.out.persistence.jdbc.h2.entity.CustomerJdbcEntity;

public interface CustomerJdbcRepository extends CrudRepository<CustomerJdbcEntity, Long> {

    @Query(
        value = "select c.* from customers c where name like :name%",
        nativeQuery = true
    )
    CustomerJdbcEntity findByName(@Param("name") String name);
    
}
