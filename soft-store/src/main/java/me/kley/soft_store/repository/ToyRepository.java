package me.kley.soft_store.repository;

import me.kley.soft_store.models.Toy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToyRepository extends JpaRepository<Toy, Long> {
    @Query("SELECT t FROM Toy t " +
            "JOIN t.market m " +
            "WHERE (:name IS NULL OR t.name LIKE :name) " +
            "AND (:marketName IS NULL OR m.name LIKE :marketName) " +
            "AND (:minCost IS NULL OR t.cost >= :minCost)" +
            "AND (:maxCost IS NULL OR t.cost <= :maxCost)")
    List<Toy> findToysByFilters(
            @Param("name") String name,
            @Param("marketName") String marketName,
            @Param("minCost") Double minCost,
            @Param("maxCost") Double maxCost);
    List<Toy> findByMarketId(Long marketId);
    Toy findToyById(Long toyId);
}
