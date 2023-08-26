package com.example.lolchampionsinvestment.domain.champion.dto;

import lombok.Data;

@Data
public class ChampionMainViewDto {

    private String name;

    private String img;

    public ChampionMainViewDto(String name, String img) {
        this.name = name;
        this.img = img;
    }
}
