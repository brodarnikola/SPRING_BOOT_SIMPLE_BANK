package com.example.simplebank.demo.service.implementation;

import com.example.simplebank.demo.dao.CustomerRepository;
import com.example.simplebank.demo.model.Customer;
import com.example.simplebank.demo.service.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer c) {
        return customerRepository.save(c);
    }

    @Override
    public Optional<Customer> findCustomerById(Integer id) {
        return customerRepository.findById(id);
    }
}
