package com.dakota.vpet.repositories;

import com.dakota.vpet.models.Pet;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends ReactiveMongoRepository<Pet, String>{
    
}
