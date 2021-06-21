package com.dakota.vpet.routes;

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
		return RouterFunctions.route().path("/users",
				builder -> builder.POST(RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::createUser))
				.build();
	}

	/*
	 *  Basically cataches any flux/mono.error() and 
	 *  handles it gracefully
	 */
	@Bean
	public WebFilter exceptionsToErrorCode(){
			return (exchange, next) -> next.filter(exchange)
							.onErrorResume(UserAlreadyExistsException.class, e -> {
								ServerHttpResponse res = exchange.getResponse();
								res.setStatusCode(HttpStatus.CONFLICT);
								return res.setComplete();
							});
	}
}
