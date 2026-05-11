package com.leoh.crud.service;

import com.leoh.crud.model.Customer;
import com.leoh.crud.respository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    // 新增：透過 Email 尋找客戶
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // 新增：分頁查詢客戶名單（預設以 id 遞增排序）
    public Page<Customer> getCustomersPage(int pageNum, int pageSize) {
        // Spring Data JPA 的頁碼是從 0 開始，所以傳入的頁碼要 - 1
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id").ascending());
        return customerRepository.findAll(pageable);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
