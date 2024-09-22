package com.tokioshool.filmotokio.config;

import java.time.Duration;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofMillis(3000))
				.setReadTimeout(Duration.ofMillis(3000))
				.build();
	}
	
	@Override
	 public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry
	    	.addResourceHandler("upload/**")
	    	.addResourceLocations("file:src/main/resources/static/images/posters/")
	    	.setCachePeriod(0);
	  }
}
