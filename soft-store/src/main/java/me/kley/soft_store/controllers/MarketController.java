package me.kley.soft_store.controllers;


import me.kley.soft_store.models.Market;
import me.kley.soft_store.models.Toy;
import me.kley.soft_store.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/markets")
public class MarketController {
    @Autowired
    private MarketService marketService;

    @GetMapping
    public List<Market> getAllMarkets() {
        return marketService.findAll();
    }

    @GetMapping("/{marketId}/toys")
    public Set<Toy> getToys(@PathVariable Long marketId) {
        return marketService.getToysForMarket(marketId);
    }
}
