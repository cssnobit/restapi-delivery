package com.estudo.estudoapirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.estudo.estudoapirest.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class EstudoApirestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstudoApirestApplication.class, args);
	}

}
