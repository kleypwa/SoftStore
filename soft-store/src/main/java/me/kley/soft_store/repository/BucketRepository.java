package me.kley.soft_store.repository;

import me.kley.soft_store.models.AppUser;
import me.kley.soft_store.models.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Optional<Bucket> findByAppUserId(Long appUserId);
    Bucket getBucketByAppUser(AppUser currentUser);
}
