package com.hpc.ship.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Data
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {

	private String title;
	private String version;
	private String basePackage;
	private String contactName;
	private String contactEmail;

	/**
	 * Initializes a springfox docket instance
	 * 
	 * @return A docket instance
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.apiInfo(getApiInfo());
	}
	
	/**
	 * Initializes a Springfoc swagger UI Configuration
	 * @return
	 */
	@Bean
	public UiConfiguration uiConfig() {
		return UiConfigurationBuilder
				.builder()
				.docExpansion(DocExpansion.LIST)
				.build();
	}

	/**
	 * Get a Springfox APIInfo
	 * 
	 * @return
	 */
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.version(version)
				.contact(new Contact(contactName, "", contactEmail))
				.build();
	}
}
