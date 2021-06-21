package com.dakota.vpet.handlers;


import java.util.concurrent.ExecutionException;

import com.dakota.vpet.exceptions.UserAlreadyExistsException;
import com.dakota.vpet.models.User;
import com.dakota.vpet.services.UserService;
import com.dakota.vpet.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // POST /users
    public Mono<ServerResponse> createUser(ServerRequest req){ 
        Mono<User> userMono = req.bodyToMono(User.class);
        
        return userMono.flatMap(user -> {
            //validation
            if(user.getUsername() == null || user.getUsername().trim() == "" 
                || user.getPassword() == null || user.getPassword().trim() == ""
                || user.getEmail() == null || user.getEmail().trim() == ""){
                
                return ServerResponse.badRequest().build();
            }else{
                try {
                    //must block so we can have the id when creating the token
                    user = userService.addUser(user).toFuture().get();

                    return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON)
                    .cookie(ResponseCookie.from("token", JWTUtil.generateToken(user)).httpOnly(true).build())
                    .bodyValue(user);
                }catch (ExecutionException ex) { // Exception that can be thrown from toFuture().get()
                    if(ex.getCause().getClass() == UserAlreadyExistsException.class){ // Check inner class and rethrow
                        return Mono.error(new UserAlreadyExistsException());
                    }else{
                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                } 
            }
        });
    }
}
