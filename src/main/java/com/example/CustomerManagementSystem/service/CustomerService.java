package com.example.CustomerManagementSystem.service;

import com.example.CustomerManagementSystem.exception.CustomerNotFoundException;
import com.example.CustomerManagementSystem.model.Customer;
import com.example.CustomerManagementSystem.repository.CustomerRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EmailService emailService;

    public CustomerService(CustomerRepository customerRepository, EmailService emailService) {
        this.customerRepository = customerRepository;
        this.emailService = emailService;
    }

    public Customer addCustomer(Customer customer) {
        Customer addedCustomer = customerRepository.save(customer);
        emailService.sendNewCustomerNotification("mehvishfansopkar8@gmail.com", customer.getName());
        return addedCustomer;
    }

    public Page<Customer> getCustomers(int page, int size, String sortBy, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerRepository.findAll(pageable);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    Customer updatedCustomer = new Customer(
                            id,
                            customerDetails.getName(),
                            customerDetails.getEmail(),
                            customerDetails.getAge(),
                            customerDetails.getCity(),
                            customerDetails.getActive(),
                            customerDetails.getRegistrationDate(),
                            customerDetails.getLastOrderDate()
                    );
                    return customerRepository.save(updatedCustomer);
                })
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.deleteById(id);
        emailService.sendCustomerRemovedNotification("mehvishfansopkar8@gmail.com", customer.getName());
    }

    public Page<Customer> findCustomersByAge(Integer age, int page, int size, String sortBy, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerRepository.findByAge(age, pageable);
    }

    public Page<Customer> findCustomersByCity(String city, int page, int size, String sortBy, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerRepository.findByCity(city, pageable);
    }

    public Page<Customer> findCustomersByActive(Boolean active, int page, int size, String sortBy, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerRepository.findByActive(active, pageable);
    }
}
