package com.leoh.crud.respository;

import com.leoh.crud.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    // 新增：透過 Email 尋找客戶
    Optional<Customer> findByEmail(String email);

}
