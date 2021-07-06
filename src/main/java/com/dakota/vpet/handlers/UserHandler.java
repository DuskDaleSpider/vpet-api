package com.dakota.vpet.handlers;

import java.util.concurrent.ExecutionException;

import com.dakota.vpet.annotations.LoggedIn;
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
    @LoggedIn
    public Mono<ServerResponse> createUser(ServerRequest req) {
        Mono<User> userMono = req.bodyToMono(User.class);

        return userMono.flatMap(user -> {
            // validation
            if (user.getUsername() == null || user.getUsername().trim() == "" || user.getPassword() == null
                    || user.getPassword().trim() == "" || user.getEmail() == null || user.getEmail().trim() == "") {

                return ServerResponse.badRequest().build();
            }
            try {
                // must block so we can have the id when creating the token
                user = userService.addUser(user).toFuture().get();

                return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON)
                        .cookie(ResponseCookie.from("token", JWTUtil.generateToken(user)).httpOnly(true).build())
                        .bodyValue(user);
            } catch (ExecutionException ex) { // Exception that can be thrown from toFuture().get()
                if (ex.getCause().getClass() == UserAlreadyExistsException.class) { // Check inner class and rethrow
                    return Mono.error(new UserAlreadyExistsException());
                } else {
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        });
    }

    public Mono<ServerResponse> loginUser(ServerRequest req) {
        Mono<User> userMono = req.bodyToMono(User.class);

        return userMono.flatMap(user -> {
            // validation
            if (user.getUsername() == null || user.getUsername().trim() == "" || user.getPassword() == null
                    || user.getPassword().trim() == "") {

                return ServerResponse.badRequest().build();
            }

            Mono<User> dbUser = userService.getUserByUsername(user.getUsername());

            return dbUser.flatMap(foundUser -> {
                if (userService.isCorrectPassword(foundUser, user.getPassword())) {
                    try {
                        return ServerResponse.ok()
                                .cookie(ResponseCookie.from("token", JWTUtil.generateToken(foundUser)).build()).build();
                    } catch (Exception ex) {
                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                } else {
                    return ServerResponse.badRequest().build();
                }
            });

        });
    }

    public Mono<ServerResponse> test(ServerRequest req) {
        Mono<User> userMono = req.bodyToMono(User.class);
        Mono<User> test = userService.test(userMono);
        return ServerResponse.ok().body(test, User.class);
    }
}
