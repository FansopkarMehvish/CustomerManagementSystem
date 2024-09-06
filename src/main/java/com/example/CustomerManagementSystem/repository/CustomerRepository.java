package com.example.CustomerManagementSystem.repository;

import com.example.CustomerManagementSystem.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findByAge(Integer age, Pageable pageable);

    Page<Customer> findByCity(String city, Pageable pageable);

    Page<Customer> findByActive(Boolean active, Pageable pageable);
}
