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

    // 修改：支援關鍵字搜尋的分頁查詢
    public Page<Customer> getCustomersPage(String keyword, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id").ascending());

        // 如果關鍵字不為空，執行模糊搜尋；否則查詢全部
        if (keyword != null && !keyword.trim().isEmpty()) {
            return customerRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(
                    keyword, keyword, keyword, pageable);
        }

        return customerRepository.findAll(pageable);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
