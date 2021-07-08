package com.dakota.vpet.models;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Pet {

    @Id
    private String id;
    private String name;
    private Integer hp;
    private Integer hunger;
    private Integer happiness;
    private String[] preferredFoods;

    public Pet() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getHunger() {
        return hunger;
    }

    public void setHunger(Integer hunger) {
        this.hunger = hunger;
    }

    public Integer getHappiness() {
        return happiness;
    }

    public void setHappiness(Integer happiness) {
        this.happiness = happiness;
    }

    public String[] getPreferredFoods() {
        return preferredFoods;
    }

    public void setPreferredFoods(String[] preferredFoods) {
        this.preferredFoods = preferredFoods;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((happiness == null) ? 0 : happiness.hashCode());
        result = prime * result + ((hp == null) ? 0 : hp.hashCode());
        result = prime * result + ((hunger == null) ? 0 : hunger.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + Arrays.hashCode(preferredFoods);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pet other = (Pet) obj;
        if (happiness == null) {
            if (other.happiness != null)
                return false;
        } else if (!happiness.equals(other.happiness))
            return false;
        if (hp == null) {
            if (other.hp != null)
                return false;
        } else if (!hp.equals(other.hp))
            return false;
        if (hunger == null) {
            if (other.hunger != null)
                return false;
        } else if (!hunger.equals(other.hunger))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (!Arrays.equals(preferredFoods, other.preferredFoods))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Pet [happiness=" + happiness + ", hp=" + hp + ", hunger=" + hunger + ", id=" + id + ", name=" + name
                + ", preferredFoods=" + Arrays.toString(preferredFoods) + "]";
    }
    
}
