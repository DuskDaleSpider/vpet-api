package com.dakota.vpet.routes;

import com.dakota.vpet.exceptions.TestException;
import com.dakota.vpet.exceptions.UserAlreadyExistsException;
import com.dakota.vpet.handlers.UserHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;

@Configuration
public class UserRoutes {

	@Bean
	public RouterFunction<ServerResponse> routes(UserHandler handler) {
		return RouterFunctions.route().path("/users", builder -> {
			builder.POST(RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::createUser);
		}).path("/login", builder -> {
			builder.POST(RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::loginUser);
		}).path("/test", builder -> {
			builder.POST(RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::test);
		}).build();
	}

	/*
	 * Basically cataches any flux/mono.error() and handles it gracefully
	 */
	@Bean
	public WebFilter exceptionsToErrorCode() {
		return (exchange, next) -> next.filter(exchange).onErrorResume(ex -> {
			ServerHttpResponse res = exchange.getResponse();
			
			if(ex instanceof UserAlreadyExistsException){
				res.setStatusCode(HttpStatus.CONFLICT);
			}else if(ex instanceof TestException){
				res.setStatusCode(HttpStatus.I_AM_A_TEAPOT);
			}

			return res.setComplete();
		});
	}
}
