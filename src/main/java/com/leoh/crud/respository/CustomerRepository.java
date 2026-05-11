package com.leoh.crud.respository;

import com.leoh.crud.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 新增：透過 Email 尋找客戶
    Optional<Customer> findByEmail(String email);

}
