package me.kley.soft_store.repository;

import me.kley.soft_store.models.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {}
