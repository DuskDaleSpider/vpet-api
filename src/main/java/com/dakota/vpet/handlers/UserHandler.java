package com.dakota.vpet.handlers;


import com.dakota.vpet.models.User;
import com.dakota.vpet.services.UserService;
import com.dakota.vpet.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class UserHandler {
    private UserService userService;

    @Autowired
    public void setUserService(UserService service) {
        this.userService = service;
    }

    public Mono<ServerResponse> createUser(ServerRequest req){ 
        Mono<User> userMono = req.bodyToMono(User.class);
        
        return userMono.flatMap(user -> {
            //validation
            if(user.getUsername() == null || user.getUsername().trim() == "" 
                || user.getPassword() == null || user.getPassword().trim() == ""
                || user.getEmail() == null || user.getEmail().trim() == ""){
                
                return ServerResponse.badRequest().build();
            }else{

                //has the password

                return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON)
                .cookie(ResponseCookie.from("token", JWTUtil.generateToken(user)).httpOnly(true).build())
                .body(userService.addUser(user), User.class); 
            }
        });
    }
}
