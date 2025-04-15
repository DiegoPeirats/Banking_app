package com.diego_peirats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Banking Application",
				description = "Backend Rest APIs for Bank",
				version = "v1.0",
				contact = @Contact(
						name = "Diego Peirats",
						email = "diegopeirats@gmail.com",
						url = "https://github.com"
						),
				license = @License(
						name = "Diego Peirats",
						url = "https://github.com"
						)
				),
		externalDocs = @ExternalDocumentation(
				description = "Diego Peirats Bank App documentation",
				url = "https://github.com")
	)
public class BankingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingAppApplication.class, args);
	}

}
