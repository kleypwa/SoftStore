package me.kley.soft_store.controllers;


import me.kley.soft_store.dto.MarketRequest;
import me.kley.soft_store.models.Market;
import me.kley.soft_store.models.Toy;
import me.kley.soft_store.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping
public class MarketController {
    @Autowired
    private MarketService marketService;

    @GetMapping("/api/markets")
    public List<Market> getAllMarkets() {
        return marketService.findAll();
    }

    @GetMapping("/api/markets/{marketId}/toys")
    public Set<Toy> getToys(@PathVariable Long marketId) {
        return marketService.getToysForMarket(marketId);
    }

    @PostMapping("/api/markets")
    public ResponseEntity<String> createMarket(@RequestBody MarketRequest marketRequest) {
        return marketService.createMarket(marketRequest);
    }
}
