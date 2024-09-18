package me.kley.soft_store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    @JsonIgnore
    private AppUser appUser;

    @OneToMany(mappedBy = "bucket", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BucketToy> bucketToys = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Set<BucketToy> getBucketToys() {
        return bucketToys;
    }

    public void setBucketToys(Set<BucketToy> bucketToys) {
        this.bucketToys = bucketToys;
    }
}
