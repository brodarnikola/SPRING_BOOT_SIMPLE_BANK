package com.example.simplebank.demo.service.interfaces;

import com.example.simplebank.demo.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer saveCustomer(Customer c);

    Optional<Customer> findCustomerById(Integer id);

    List<Customer> createDummyCustomers(Integer numberOfCustomers);

    List<Customer> findAllCustomers();
}
