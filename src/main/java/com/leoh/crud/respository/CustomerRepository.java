package com.leoh.crud.respository;

import com.leoh.crud.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 新增：透過 Email 尋找客戶
    Optional<Customer> findByEmail(String email);

    // 新增：同時模糊搜尋 姓、名、Email，並支援分頁
    Page<Customer> findByFirstNameContainingOrLastNameContainingOrEmailContaining(
            String firstName, String lastName, String email, Pageable pageable);

}
