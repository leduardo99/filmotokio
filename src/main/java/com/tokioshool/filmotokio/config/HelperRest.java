package com.tokioshool.filmotokio.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokioshool.filmotokio.controller.FilmController;
import com.tokioshool.filmotokio.domain.User;

public class HelperRest {
	
	public static User getAuthenticationUser() {
		User user = new User();
		user.setUsername(FilmController.usernameRest);
		user.setPassword(FilmController.passwordRest);
		return user;
	}
	
	public static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
	
	public static String getBody(final User user) throws JsonProcessingException{
		return new ObjectMapper().writeValueAsString(user);
	}
}
