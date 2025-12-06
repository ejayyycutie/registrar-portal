package com.nsc.registrar_portal; // <--- CHECK MO KUNG TAMA ITO SA FOLDER MO

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// ITO ANG SOLUSYON: Tinitiyak natin na babasahin niya ang tamang folder
@EntityScan(basePackages = "com.nsc.registrar_portal.entity")
@EnableJpaRepositories(basePackages = "com.nsc.registrar_portal.repository")
public class RegistrarPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistrarPortalApplication.class, args);
    }
}