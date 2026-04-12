package com.riu.hotelsearch.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "searches", indexes = {
        @Index(name = "idx_search_count", columnList = "hotelId, check_in, check_out, ages_hash")
})
@Getter
@Setter
@NoArgsConstructor
public class SearchEntity {

    @Id
    private String searchId;

    private String hotelId;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    @Convert(converter = AgesConverter.class)
    @Column(columnDefinition = "text")
    private List<Integer> ages;

    @Column(name = "ages_hash")
    private Integer agesHash;

    public SearchEntity(String searchId, String hotelId, LocalDate checkIn, LocalDate checkOut,
                       List<Integer> ages, Integer agesHash) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
        this.agesHash = agesHash;
    }
}
