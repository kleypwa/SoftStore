package me.kley.soft_store.service;

import me.kley.soft_store.models.Market;
import me.kley.soft_store.models.Toy;
import me.kley.soft_store.repository.MarketRepository;
import me.kley.soft_store.repository.ToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ToyService {
    @Autowired
    private ToyRepository toyRepository;
    @Autowired
    private MarketRepository marketRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    public List<Toy> findToys(String name, String marketName, Double minCost, Double maxCost) {
        return toyRepository.findToysByFilters(name, marketName, minCost, maxCost);
    }

    public void saveToy(Toy toy) {
        toyRepository.save(toy);
    }

    public List<Toy> findByMarketId(Long marketId) {
        return toyRepository.findByMarketId(marketId);
    }

    public Toy getToyById(Long toyId) {
        return toyRepository.findToyById(toyId);
    }

    public ResponseEntity<String> createToy(String name, String description, Long cost, Long marketId, MultipartFile image) {
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

        this.saveToy(toy);

        return new ResponseEntity<>("Toy created successfully", HttpStatus.CREATED);
    }
}
