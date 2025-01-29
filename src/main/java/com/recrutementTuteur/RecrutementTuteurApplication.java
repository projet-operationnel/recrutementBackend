



package com.recrutementTuteur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories("com.recrutementTuteur.data.repository")
@EntityScan("com.recrutementTuteur.data.entity")
public class RecrutementTuteurApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecrutementTuteurApplication.class, args);
	}
}

/*@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories("com.recrutementTuteur.data.repository")
@EntityScan("com.recrutementTuteur.data.entity")
@ComponentScan(basePackages = {
		"com.recrutementTuteur.web.controller",
		"com.recrutementTuteur.services",
		"com.recrutementTuteur.data.repository",
		"com.recrutementTuteur.data.enums",
		"com.recrutementTuteur.services.impl",
		"com.recrutementTuteur.data.entity",
		"com.recrutementTuteur.security",
		"com.recrutementTuteur.web.dto.requests",
		"com.recrutementTuteur.web.dto.response",
		"com.recrutementTuteur.exceptions",
		"com.recrutementTuteur.validator",
		"com.recrutementTuteur.config"
})
public class RecrutementTuteurApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecrutementTuteurApplication.class, args);
	}
}*/
