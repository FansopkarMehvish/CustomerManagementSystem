package com.example.CustomerManagementSystem.controller;

import com.example.CustomerManagementSystem.model.Customer;
import com.example.CustomerManagementSystem.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer addedCustomer = customerService.addCustomer(customer);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<Page<Customer>> getAllCustomers(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String sortOrder) {
        Page<Customer> customers = customerService.getCustomers(page, size, sortBy, sortOrder);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/getCustomerById")
    public ResponseEntity<Customer> getCustomerById(@RequestParam Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestParam Long id, @Valid @RequestBody Customer customerDetails) {
            Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<Void> deleteCustomer(@RequestParam Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/filter/age")
    public ResponseEntity<Page<Customer>> filterCustomersByAge(@RequestParam Integer age, @RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String sortOrder) {
        Page<Customer> customers = customerService.findCustomersByAge(age, page, size, sortBy, sortOrder);
        if (customers.hasContent()) {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/filter/city")
    public ResponseEntity<Page<Customer>> filterCustomersByCity(@RequestParam String city, @RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String sortOrder) {
        Page<Customer> customers = customerService.findCustomersByCity(city, page, size, sortBy, sortOrder);
        if (customers.hasContent()) {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/filter/active")
    public ResponseEntity<Page<Customer>> filterCustomersByActive(@RequestParam Boolean active, @RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String sortOrder) {
        Page<Customer> customers = customerService.findCustomersByActive(active, page, size, sortBy, sortOrder);
        if (customers.hasContent()) {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
