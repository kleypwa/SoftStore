package me.kley.soft_store.repository;

import me.kley.soft_store.models.BucketToy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketToyRepository extends JpaRepository<BucketToy, Long> {
    BucketToy findByBucketId(Long bucketId);
    Optional<BucketToy> findByToyId(Long toyId);
}
