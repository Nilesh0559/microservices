package com.project.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(info = @Info(title = "Accounts microservices REST API documentation", description = "Accounts microservices REST API documentation", version = "v1", contact = @Contact(name = "Nilesh Gajendragadkar", email = "nilesh@gmail.com", url = "https://demo.url.com"), license = @License(name = "Apache 2.0", url = "https://demo.url.com")), externalDocs = @ExternalDocumentation(description = "Accounts microservices REST API documentation", url = "https://demo.url.com"))
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
