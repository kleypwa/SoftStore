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
    @Autowired
    private MarketRepository marketRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

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

        Optional<Market> marketOptional = marketRepository.findById(marketId);

        if (marketOptional.isEmpty()) {
            return new ResponseEntity<>("Market not found", HttpStatus.BAD_REQUEST);
        }

        String imageFileName = null;
        if (image != null && !image.isEmpty()) {
            try {
                imageFileName = image.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + imageFileName);
                Files.write(path, image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Failed to save image", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        Toy toy = new Toy();
        toy.setName(name);
        toy.setDescription(description);
        toy.setCost(cost);
        toy.setImage(imageFileName);
        toy.setMarket(marketOptional.get());

        toyService.saveToy(toy);

        return new ResponseEntity<>("Toy created successfully", HttpStatus.CREATED);
    }
}
