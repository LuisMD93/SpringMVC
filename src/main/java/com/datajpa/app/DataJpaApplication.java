package com.datajpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DataJpaApplication implements CommandLineRunner{

     @Autowired 
     BCryptPasswordEncoder crypto;

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}


    @Override
    public void run(String... args) throws  Exception{

        String password = "12345";
 
        for(int i = 0; i <2; i++){
           String cryptoPassword = crypto.encode(password);
            System.out.println("Password # "+i+" : "+cryptoPassword);   
        }
    }

}
