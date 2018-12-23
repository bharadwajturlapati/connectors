package com.company.fileservice.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.company.fileservice.controller.FileServiceHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
@EnableWebMvc
public class ServiceConfiguration extends WebMvcConfigurationSupport {

	@Bean
	public FileServiceHandler getFileServiceHandler() {
		return new FileServiceHandler();
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(100000);
		return multipartResolver;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(createGsonHttpMessageConverter());
		super.configureMessageConverters(converters);
	}

	private GsonHttpMessageConverter createGsonHttpMessageConverter() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create();

		GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
		gsonConverter.setGson(gson);

		return gsonConverter;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("securedPage");
		registry.addViewController("loginFailure");
	}
}
