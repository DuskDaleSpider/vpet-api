package com.dakota.vpet.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "activePets")
public class ActivePet extends Pet{
    
    protected String owner;

    public String getOwner(){
        return this.owner;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }
    
}
