package com.dakota.vpet.repositories;

import com.dakota.vpet.models.ActivePet;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface ActivePetRepository extends ReactiveMongoRepository<ActivePet, String>{
    public Flux<ActivePet> findAllByOwner(String owner);
}
