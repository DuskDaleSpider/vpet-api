package com.dakota.vpet.repositories;

import com.dakota.vpet.models.User;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<Boolean> existsByUsername(String username);

    Mono<User> findByUsername(String username);
}
