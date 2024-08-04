package com.example.simplebank.demo;

import com.example.simplebank.demo.service.implementation.TransactionServiceImpl;
import com.example.simplebank.demo.service.init.InitialImportService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class SimpleBankApplication implements ApplicationRunner {

	@Autowired
	InitialImportService initialImportService;
	@Autowired
	TransactionServiceImpl transactionService;

	public static void main(String[] args) {
		SpringApplication.run(SimpleBankApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		initialImportService.run();
	}
}
