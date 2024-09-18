package me.kley.soft_store.service;

import me.kley.soft_store.models.AppUser;
import me.kley.soft_store.models.Bucket;
import me.kley.soft_store.models.BucketToy;
import me.kley.soft_store.repository.BucketRepository;
import me.kley.soft_store.repository.BucketToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BucketService {
    private final BucketToyRepository bucketToyRepository;
    private final BucketRepository bucketRepository;

    @Autowired
    public BucketService(BucketToyRepository bucketToyRepository, BucketRepository bucketRepository) {
        this.bucketToyRepository = bucketToyRepository;
        this.bucketRepository = bucketRepository;
    }

    public BucketToy updateToyCount(Long toyId, int count) {
        BucketToy bucketToy = bucketToyRepository.findByToyId(toyId);
        bucketToy.setCount(count);
        return bucketToyRepository.save(bucketToy);
    }

    public void deleteBucketToy(BucketToy bucketToy) {
        bucketToyRepository.delete(bucketToy);
    }

    public Optional<BucketToy> findByIdBucketToy(Long id) {
        return bucketToyRepository.findById(id);
    }

    public Bucket saveBucket(Bucket bucket) {
        return bucketRepository.save(bucket);
    }

    public Bucket getBucketByAppUser(AppUser currentUser) {
        return bucketRepository.getBucketByAppUser(currentUser);
    }

    public Optional<Bucket> findByAppUserId(Long id) {
        return bucketRepository.findByAppUserId(id);
    }
}
