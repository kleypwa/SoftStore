package me.kley.soft_store.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Toy> toys = new HashSet<>();

    private String name;
    private String description;

    public Market() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Toy> getToys() {
        return toys;
    }

    public void setToys(Set<Toy> toys) {
        this.toys = toys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Market(Long id, Set<Toy> toys, String name, String description) {
        this.id = id;
        this.toys = toys;
        this.name = name;
        this.description = description;
    }
}
