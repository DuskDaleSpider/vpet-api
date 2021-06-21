package com.dakota.vpet.services;

import com.dakota.vpet.exceptions.UserAlreadyExistsException;
import com.dakota.vpet.models.User;
import com.dakota.vpet.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    private UserRepository userRepo;
    private PasswordEncoder passEncoder;

    @Autowired
    public void setUserRepository(UserRepository repo) {
        this.userRepo = repo;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder encoder){
        this.passEncoder = encoder;
    }


    public Mono<User> addUser(User user) {
        return userRepo.existsByUsername(user.getUsername())
            .flatMap(exists -> {
                if(!exists.booleanValue()) {
                    
                    user.setPassword(passEncoder.encode(user.getPassword()));
                    user.setRole(User.Role.PLAYER); // Set new players to base role by default

                    return userRepo.insert(user);
                }else {
                    return Mono.error(new UserAlreadyExistsException());
                }
            });
    }
    
}
