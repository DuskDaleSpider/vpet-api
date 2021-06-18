package com.dakota.vpet.repositories;

import com.dakota.vpet.models.User;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
