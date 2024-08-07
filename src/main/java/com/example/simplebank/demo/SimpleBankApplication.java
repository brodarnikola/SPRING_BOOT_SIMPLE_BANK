package com.example.simplebank.demo;

import com.example.simplebank.demo.service.init.InitialAccountImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimpleBankApplication implements ApplicationRunner {

	@Autowired
	InitialAccountImport initialAccountImport;

	public static void main(String[] args) {
		SpringApplication.run(SimpleBankApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		initialAccountImport.run();
	}
}
