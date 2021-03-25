package com.registration.RegistrationApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsUtil{	

			 @Bean
				public WebMvcConfigurer corsConfigurer() {
					return new WebMvcConfigurer() {
						@Override
						public void addCorsMappings(CorsRegistry registry) {
							registry.addMapping("/**").allowedMethods("*").allowedHeaders("*").
							allowedOrigins("*").exposedHeaders("Authorization");
						}
					};
			 }
			 
}
