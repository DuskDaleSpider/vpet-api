package com.dakota.vpet.handlers;

import com.dakota.vpet.models.User;
import com.dakota.vpet.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {
    private UserService userService;

    @Autowired
    public void setUserService(UserService service) {
        this.userService = service;
    }

    public Mono<ServerResponse> createUser(ServerRequest req){ 
        Mono<User> user = req.bodyToMono(User.class);
        Flux<User> result = userService.addUser(user);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(result, User.class);
    }
}
