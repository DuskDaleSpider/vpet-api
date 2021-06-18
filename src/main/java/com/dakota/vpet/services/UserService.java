package com.dakota.vpet.services;

import com.dakota.vpet.models.User;
import com.dakota.vpet.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public Flux<User> addUser(Mono<User> user) {
        return userRepo.insert(user.flux());
    }
    
}
