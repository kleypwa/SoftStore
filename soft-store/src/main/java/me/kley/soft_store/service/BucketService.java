package me.kley.soft_store.service;

import me.kley.soft_store.dto.ToyWithCountDTO;
import me.kley.soft_store.models.AppUser;
import me.kley.soft_store.models.Bucket;
import me.kley.soft_store.models.BucketToy;
import me.kley.soft_store.models.Toy;
import me.kley.soft_store.repository.BucketRepository;
import me.kley.soft_store.repository.BucketToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BucketService {
    private final BucketToyRepository bucketToyRepository;
    private final BucketRepository bucketRepository;
    private final AppUserService appUserService;
    private final ToyService toyService;

    @Autowired
    public BucketService(BucketToyRepository bucketToyRepository, BucketRepository bucketRepository, AppUserService appUserService, ToyService toyService) {
        this.bucketToyRepository = bucketToyRepository;
        this.bucketRepository = bucketRepository;
        this.appUserService = appUserService;
        this.toyService = toyService;
    }

    private BucketToy updateToyCount(Long toyId, int count) {
        Optional<BucketToy> bucketToyOptional = bucketToyRepository.findByToyId(toyId);
        if (bucketToyOptional.isPresent()) {
            BucketToy bucketToy = bucketToyOptional.get();
            bucketToy.setCount(count);
            return bucketToyRepository.save(bucketToy);
        } else {
            throw new UsernameNotFoundException("");
        }
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

    public ResponseEntity<String> deleteBucketToy(Long id) {
        Optional<BucketToy> bucketToyOptional = findByToyIdInBucketToy(id);

        if (bucketToyOptional.isPresent()) {
            bucketToyRepository.delete(bucketToyOptional.get());
            return ResponseEntity.ok("Delete success.");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateToyQuantity(Map<String, Object> request) {
        Long toyId = Long.valueOf(request.get("toyId").toString());
        int count = Integer.parseInt(request.get("count").toString());

        BucketToy updatedBucketToy = updateToyCount(toyId, count);

        return ResponseEntity.ok(updatedBucketToy);
    }

    public ResponseEntity<String> addToBucket(Long toyId) {
        AppUser currentUser = appUserService.getCurrentAuthenticatedUser();
        Bucket bucket = getBucketByAppUser(currentUser);

        if (bucket == null) {
            bucket = new Bucket();
            bucket.setAppUser(currentUser);
            bucket = bucketRepository.save(bucket);
        }

        Toy toy = toyService.getToyById(toyId);
        if (toy == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Toy not found");
        }

        Optional<BucketToy> existingBucketToy = bucket.getBucketToys().stream()
                .filter(bucketToy -> bucketToy.getToy().getId().equals(toyId))
                .findFirst();

        if (existingBucketToy.isPresent()) {
            BucketToy bucketToy = existingBucketToy.get();
            bucketToy.setCount(bucketToy.getCount() + 1);
        } else {
            BucketToy newBucketToy = new BucketToy();
            newBucketToy.setBucket(bucket);
            newBucketToy.setToy(toy);
            newBucketToy.setCount(1);
            bucket.getBucketToys().add(newBucketToy);
        }

        bucketRepository.save(bucket);

        return ResponseEntity.ok("Toy added to bucket successfully");
    }

    public List<ToyWithCountDTO> getToysForCurrentUserBucket() {
        AppUser currentUser = appUserService.getCurrentAuthenticatedUser();
        Optional<Bucket> bucket = findByAppUserId(currentUser.getId());

        return bucket.map(b -> b.getBucketToys().stream()
                        .sorted(Comparator.comparing(BucketToy::getId))
                        .map(bucketToy -> new ToyWithCountDTO(bucketToy.getToy(), bucketToy.getCount()))  // Извлекаем игрушки и количество
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }
}
