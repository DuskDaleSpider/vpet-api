package com.dakota.vpet.services;

import com.dakota.vpet.models.Pet;
import com.dakota.vpet.repositories.PetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class PetService {
    private PetRepository repository;

    @Autowired
    public void setPetRepository(PetRepository repo){
        this.repository = repo;
    }

    public Mono<Pet> addPet(Pet pet) {
        return repository.insert(pet);
    }
}
