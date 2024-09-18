package me.kley.soft_store.repository;

import me.kley.soft_store.models.BucketToy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketToyRepository extends JpaRepository<BucketToy, Long> {
    BucketToy findByBucketId(Long bucketId);
    BucketToy findByToyId(Long toyId);
}
