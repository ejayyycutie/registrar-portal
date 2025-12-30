package com.nsc.registrar_portal; // Check kung tama ang package!

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan; // Import this

@SpringBootApplication
@ComponentScan(basePackages = "com.nsc") // <--- IDAGDAG MO ITO
public class RegistrarPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistrarPortalApplication.class, args);
    }

}