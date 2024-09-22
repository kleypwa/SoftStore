package me.kley.soft_store.controllers;

import me.kley.soft_store.models.Market;
import me.kley.soft_store.models.Toy;
import me.kley.soft_store.repository.MarketRepository;
import me.kley.soft_store.service.ToyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class ToyController {
    @Autowired
    private ToyService toyService;

    @GetMapping("/api/toys")
    public List<Toy> getToys(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String marketName,
            @RequestParam(required = false) Double minCost,
            @RequestParam(required = false) Double maxCost
    ) {
        return toyService.findToys(name, marketName, minCost, maxCost);
    }

    @GetMapping("/api/toys/{marketId}")
    public List<Toy> getToysByMarket(@PathVariable Long marketId) {
        return toyService.findByMarketId(marketId);
    }

    @PostMapping("/api/toys")
    public ResponseEntity<String> createToy(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("cost") Long cost,
            @RequestParam("marketId") Long marketId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        return toyService.createToy(name, description, cost, marketId, image);
    }
}
