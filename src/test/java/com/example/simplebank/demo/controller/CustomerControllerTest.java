package com.example.simplebank.demo.controller;

import com.example.simplebank.demo.model.Customer;
import com.example.simplebank.demo.model.dto.CustomerResponseDTO;
import com.example.simplebank.demo.service.interfaces.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer realMockCustomer;


    @BeforeEach
    void setUp() {

        realMockCustomer = new Customer();
        realMockCustomer.setName("Michale Jordan");
        realMockCustomer.setEmail("michael.jordan@example.com");
    }

    @Test
    void testGetCustomerByIdFound() {
        when(customerService.findCustomerById(1)).thenReturn(Optional.of(realMockCustomer));

        ResponseEntity<?> responseEntity = customerController.getCustomerById(1);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(realMockCustomer);
    }

    @Test
    void testGetCustomerByIdNotFound() {
        when(customerService.findCustomerById(2)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = customerController.getCustomerById(2);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isNull();
    }
}
