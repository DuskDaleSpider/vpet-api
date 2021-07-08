package com.dakota.vpet.routes;

import com.dakota.vpet.handlers.PetHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PetRouter {
    
    @Bean
    public RouterFunction<ServerResponse> petRoutes(PetHandler handler){
        return RouterFunctions.route().path("/pets", builder -> {
            builder.POST(RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::addPet);
        }).build();
    }

}
