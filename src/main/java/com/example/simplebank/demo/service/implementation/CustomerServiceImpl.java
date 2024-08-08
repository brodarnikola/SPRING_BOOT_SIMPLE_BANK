package com.example.simplebank.demo.service.implementation;

import com.example.simplebank.demo.dao.CustomerRepository;
import com.example.simplebank.demo.model.Customer;
import com.example.simplebank.demo.service.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<Customer> createDummyCustomers(Integer numberOfCustomers) {

        try {
            List<Customer> customers = new ArrayList<>();

            String email1 = "brodarnikola60@gmail.com";
            String email2 = "brodarnikola7@gmail.com";
            String email3 = "brodarnikola54@gmail.com";


            for (int i = 0; i < numberOfCustomers; i++) {
                Customer customer = new Customer();
                if (i == 0) {
                    createNewDummyCustomer("Michael Jordan", "Chicago, Augusta Senoe 23", email1, customer);
                } else if (i == 1) {
                    createNewDummyCustomer("Drazen Petrovic", "Zagreb, Krapinska ulica, 54", email2, customer);
                } else {
                    createNewDummyCustomer("Toni Kukoc", "Split, Rade koncara, 31", email3, customer);
                }
                customers.add(customer);
            }

            customerRepository.saveAll(customers);
            return customers;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void createNewDummyCustomer(String name, String address, String email, Customer customer) {
        customer.setName(name);
        customer.setAddress(address);
        customer.setEmail(email);
    }

    @Override
    public List<Customer> findAllCustomers() {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }
}
