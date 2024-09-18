package me.kley.soft_store.service;

import me.kley.soft_store.models.Market;
import me.kley.soft_store.models.Toy;
import me.kley.soft_store.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MarketService {

    @Autowired
    private MarketRepository marketRepository;

    public Set<Toy> getToysForMarket(Long marketId) {
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new RuntimeException("Market not found"));
        return market.getToys();
    }

    public List<Market> findAll() {
        return marketRepository.findAll();
    }
}

