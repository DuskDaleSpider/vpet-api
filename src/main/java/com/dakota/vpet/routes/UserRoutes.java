package com.dakota.vpet.routes;

import com.dakota.vpet.handlers.UserHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRoutes {
	
	@Bean
	public RouterFunction<ServerResponse> routes(UserHandler handler) {
		return RouterFunctions.route().path("/",
				builder -> builder.POST(handler::createUser))
				.build();
	}

}
