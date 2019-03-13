package org.springframework.samples.completefitnesstracker.util;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages="org.springframework.samples.completefitnesstracker.rest")
public class ApplicationSwaggerConfig {

   @Bean
   public Docket customDocket(){
      return new Docket(DocumentationType.SWAGGER_2)
    		  .select()
              .apis(RequestHandlerSelectors.any())
              .paths(PathSelectors.any())
              .build()
              .apiInfo(getApiInfo());
   }

   private ApiInfo getApiInfo() {
	   return new ApiInfo(
		"REST Completefitnesstracker backend Api Documentation",
		"This is REST API documentation of the Spring Completefitnesstracker backend. If authentication is enabled, when calling the APIs use admin/admin",
		"1.0",
		"Completefitnesstracker backend terms of service",
		new Contact(
				"Vitaliy Fedoriv",
				"https://github.com/spring-completefitnesstracker/spring-completefitnesstracker-rest",
				"vitaliy.fedoriv@gmail.com"),
		"Apache 2.0",
		"http://www.apache.org/licenses/LICENSE-2.0");
   }


}
