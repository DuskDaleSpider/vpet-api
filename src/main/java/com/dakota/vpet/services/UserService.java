package com.dakota.vpet.services;

import com.dakota.vpet.exceptions.TestException;
import com.dakota.vpet.exceptions.UserAlreadyExistsException;
import com.dakota.vpet.models.ActivePet;
import com.dakota.vpet.models.User;
import com.dakota.vpet.repositories.ActivePetRepository;
import com.dakota.vpet.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private UserRepository userRepo;
    private PasswordEncoder passEncoder;
    private ActivePetRepository activePetRepo;

    @Autowired
    public void setUserRepository(UserRepository repo) {
        this.userRepo = repo;
    }

    @Autowired
    public void setPetRepository(ActivePetRepository repo){
        this.activePetRepo = repo;
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

                    return userRepo.insert(user);
                }else {
                    return Mono.error(new UserAlreadyExistsException());
                }
            });
    }
    
    public Mono<User> getUserByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public boolean isCorrectPassword(User user, String password){
        return passEncoder.matches(password, user.getPassword());
    }

	public Mono<User> test(Mono<User> userMono) {
		return userMono.flatMap(user -> {
            System.err.println(user.getUsername());
            return user.getUsername().equals("Dakota") ? Mono.just(user) : Mono.error(new TestException());
        });
	}

    public Flux<ActivePet> getUserPets(String username) {
        return activePetRepo.findAllByOwner(username);
    }

    public Mono<ActivePet> addUserPet(ActivePet testPet) {
        return activePetRepo.insert(testPet);
    }

}
