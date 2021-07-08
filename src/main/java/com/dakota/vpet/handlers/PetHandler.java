package com.dakota.vpet.handlers;

import com.dakota.vpet.annotations.LoggedIn;
import com.dakota.vpet.models.Pet;
import com.dakota.vpet.models.User.Role;
import com.dakota.vpet.services.PetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class PetHandler {

    private PetService petService;

    @Autowired
    public void setPetService(PetService service) {
        this.petService = service;
    }

    @LoggedIn(minRole = Role.ADMIN)
    public Mono<ServerResponse> addPet(ServerRequest req) {
        Mono<Pet> petMono = req.bodyToMono(Pet.class);

        return petMono.flatMap(petInput -> {
            if (petInput.getName() == null || petInput.getName().isEmpty() || petInput.getHp() == null
                    || petInput.getHp() < 0 || petInput.getHunger() == null || petInput.getHunger() < 0
                    || petInput.getHappiness() == null || petInput.getHappiness() < 0) {
                ServerResponse.badRequest().build();
            }

            Mono<Pet> insertedPet = petService.addPet(petInput);

            return ServerResponse.ok().body(insertedPet, Pet.class);

        });
    }

}
