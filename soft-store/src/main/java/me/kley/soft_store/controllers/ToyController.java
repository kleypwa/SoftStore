package me.kley.soft_store.controllers;


import me.kley.soft_store.dto.ToyRequest;
import me.kley.soft_store.models.Market;
import me.kley.soft_store.models.Toy;
import me.kley.soft_store.repository.MarketRepository;
import me.kley.soft_store.service.ToyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class ToyController {
    @Autowired
    private ToyService toyService;
    @Autowired
    private MarketRepository marketRepository;

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
    public ResponseEntity<String> createToy(@RequestBody ToyRequest toyRequest) {
        Optional<Market> marketOptional = marketRepository.findById(toyRequest.getMarketId());

        if (marketOptional.isEmpty()) {
            return new ResponseEntity<>("Market not found", HttpStatus.BAD_REQUEST);
        }

        Toy toy = new Toy();
        toy.setName(toyRequest.getName());
        toy.setDescription(toyRequest.getDescription());
        toy.setCost(toyRequest.getCost());
        toy.setImage(toyRequest.getImage());
        toy.setMarket(marketOptional.get());

        toyService.saveToy(toy);

        return new ResponseEntity<>("Toy created successfully", HttpStatus.CREATED);
    }
}
