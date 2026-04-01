package com.riu.hotelsearch.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SearchJpaRepository extends JpaRepository<SearchEntity, String> {

    @Query(value = "SELECT COUNT(*) FROM searches s WHERE s.hotel_id = :hotelId AND s.check_in = :checkIn AND s.check_out = :checkOut AND s.ages = :ages", nativeQuery = true)
    long countBySearchFields(
            @Param("hotelId") String hotelId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("ages") String ages
    );
}
