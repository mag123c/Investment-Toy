package com.example.lolchampionsinvestment.domain.champion.service;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ChampionRandomPriceChange {

    private ChampionRepository championRepository;

    private int championSize;

    public ChampionRandomPriceChange(ChampionRepository championRepository) {
        this.championRepository = championRepository;
        this.championSize = championRepository.findAll().size();
    }
    public void priceChange() {
        List<Integer> randomList = new ArrayList<>();
        for(int i = 1; i <= championSize; i ++) {
            randomList.add(i);
        }
        Collections.shuffle(randomList);

        int ranNum = (int) Math.random() * 164 + 1;

    }

}
