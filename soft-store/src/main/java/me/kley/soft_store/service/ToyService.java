package me.kley.soft_store.service;

import me.kley.soft_store.models.Toy;
import me.kley.soft_store.repository.ToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyService {

    @Autowired
    private ToyRepository toyRepository;

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
}
