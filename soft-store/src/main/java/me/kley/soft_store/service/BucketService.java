package me.kley.soft_store.service;

import me.kley.soft_store.models.AppUser;
import me.kley.soft_store.models.Bucket;
import me.kley.soft_store.models.BucketToy;
import me.kley.soft_store.repository.BucketRepository;
import me.kley.soft_store.repository.BucketToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        Optional<BucketToy> bucketToyOptional = bucketToyRepository.findByToyId(toyId);
        if (bucketToyOptional.isPresent()) {
            BucketToy bucketToy = bucketToyOptional.get();
            bucketToy.setCount(count);
            return bucketToyRepository.save(bucketToy);
        } else {
            throw new UsernameNotFoundException("");
        }
    }

    public void deleteBucketToy(BucketToy bucketToy) {
        bucketToyRepository.delete(bucketToy);
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

    public Optional<BucketToy> findByToyIdInBucketToy(Long toyId) {
        return bucketToyRepository.findByToyId(toyId);
    }
}
