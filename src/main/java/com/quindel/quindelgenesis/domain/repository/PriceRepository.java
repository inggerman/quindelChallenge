package com.quindel.quindelgenesis.domain.repository;


import com.quindel.quindelgenesis.domain.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing database operations on Price entities.
 */
public interface PriceRepository extends JpaRepository<Price, Long> {

    /**
     * Retrieves a list of prices filtered by the specified start date, end date,
     * product ID, and brand ID, within the given Pageable limits.
     *
     * @param startDate Start date for filtering.
     * @param productId Product ID for filtering.
     * @param brandId   Brand ID for filtering.
     * @return A list of prices matching the criteria.
     */
    @Query("SELECT p FROM Price p WHERE (:startDate >= p.startDate   AND :endDate <= p.endDate ) " +
            "AND"+" p.productId = :productId AND p.brand.id = :brandId ORDER BY p.priceList LIMIT :limitPrice")
    Optional<List<Price>> findPricesByCriteria(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("productId") Integer productId,
            @Param("brandId") Long brandId,
            @Param("limitPrice") int limit);
}