package com.example.lolchampionsinvestment.domain.champion;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Champion")
public class Champion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private String description;

    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;


    @Builder
    public Champion(String name, int price, String description, LocalDateTime createDateTime) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createDateTime = createDateTime;
    }
}
