package me.kley.soft_store.controllers;

import me.kley.soft_store.dto.ToyWithCountDTO;
import me.kley.soft_store.service.BucketService;
import me.kley.soft_store.service.ToyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping
public class BucketController {
    @Autowired
    private BucketService bucketService;

    @GetMapping("/bucket")
    public String bucket() {
        return "bucket";
    }

    @GetMapping("/api/bucket")
    public List<ToyWithCountDTO> getToysForCurrentUserBucket() {
        return bucketService.getToysForCurrentUserBucket();
    }

    @PostMapping("/api/bucket/add/{toyId}")
    public ResponseEntity<String> addToBucket(@PathVariable Long toyId) {
        return bucketService.addToBucket(toyId);
    }

    @PostMapping("/api/bucket/update-quantity")
    public ResponseEntity<?> updateToyQuantity(@RequestBody Map<String, Object> request) {
        return bucketService.updateToyQuantity(request);
    }

    @DeleteMapping("/api/bucket/{id}")
    public ResponseEntity<String> deleteBucketToy(@PathVariable Long id) {
        return bucketService.deleteBucketToy(id);
    }
}
