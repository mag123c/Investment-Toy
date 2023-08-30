package com.example.lolchampionsinvestment.domain.trading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChampionTradingDto {

    private String name;
    private int price;
    private int amount;

}
