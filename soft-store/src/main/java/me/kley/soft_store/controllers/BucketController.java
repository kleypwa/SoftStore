package me.kley.soft_store.controllers;


import me.kley.soft_store.dto.ToyWithCountDTO;
import me.kley.soft_store.models.AppUser;
import me.kley.soft_store.models.Bucket;
import me.kley.soft_store.models.BucketToy;
import me.kley.soft_store.models.Toy;
import me.kley.soft_store.service.AppUserService;
import me.kley.soft_store.service.BucketService;
import me.kley.soft_store.service.ToyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bucket")
public class BucketController {
    private final ToyService toyService;
    private final AppUserService appUserService;
    private final BucketService bucketService;

    @Autowired
    public BucketController(ToyService toyService, AppUserService appUserService, BucketService bucketService) {
        this.toyService = toyService;
        this.appUserService = appUserService;
        this.bucketService = bucketService;
    }

    @GetMapping
    public List<ToyWithCountDTO> getToysForCurrentUserBucket() {
        AppUser currentUser = appUserService.getCurrentAuthenticatedUser();
        Optional<Bucket> bucket = bucketService.findByAppUserId(currentUser.getId());

        return bucket.map(b -> b.getBucketToys().stream()
                        .map(bucketToy -> new ToyWithCountDTO(bucketToy.getToy(), bucketToy.getCount()))  // Извлекаем игрушки и количество
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

    @PostMapping("/add/{toyId}")
    public ResponseEntity<String> addToBucket(@PathVariable Long toyId) {
        AppUser currentUser = appUserService.getCurrentAuthenticatedUser();
        Bucket bucket = bucketService.getBucketByAppUser(currentUser);

        if (bucket == null) {
            bucket = new Bucket();
            bucket.setAppUser(currentUser);
            bucket = bucketService.saveBucket(bucket);
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

        bucketService.saveBucket(bucket);

        return ResponseEntity.ok("Toy added to bucket successfully");
    }

    @PostMapping("/update-quantity")
    public ResponseEntity<?> updateToyQuantity(@RequestBody Map<String, Object> request) {
        Long toyId = Long.valueOf(request.get("toyId").toString());
        int count = Integer.parseInt(request.get("count").toString());

        BucketToy updatedBucketToy = bucketService.updateToyCount(toyId, count);

        return ResponseEntity.ok(updatedBucketToy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBucketToy(@PathVariable Long id) {
        Optional<BucketToy> bucketToyOptional = bucketService.findByIdBucketToy(id);

        if (bucketToyOptional.isPresent()) {
            bucketService.deleteBucketToy(bucketToyOptional.get());
            return ResponseEntity.ok("Delete success.");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
