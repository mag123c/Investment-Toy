package com.example.lolchampionsinvestment.domain.champion.service;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionCustomDao;
import com.example.lolchampionsinvestment.domain.champion.dao.ChampionPriceChangeLogRepository;
import com.example.lolchampionsinvestment.domain.champion.dao.ChampionPriceLogRepository;
import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import com.example.lolchampionsinvestment.domain.champion.domain.ChampionPriceChangeLog;
import com.example.lolchampionsinvestment.domain.champion.domain.ChampionPriceLog;
import com.example.lolchampionsinvestment.domain.champion.dto.ChampionPriceDto;
import com.example.lolchampionsinvestment.domain.trading.handler.ChampionsWS;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class ChampionRandomUpdater {

    @Autowired
    ChampionRepository championRepository;
    @Autowired
    ChampionPriceLogRepository championPriceLogRepository;
    @Autowired
    ChampionPriceChangeLogRepository championPriceChangeLogRepository;
    @Autowired
    ChampionCustomDao championCustomDao;
    @Autowired
    ChampionsWS championsWS;

    private Map<String, Integer> championsDelays = new HashMap<>();
    private Map<String, Integer> championsPrices = new HashMap<>();
    private Random random = new Random();


    @PostConstruct
    public void initChampionDelays() {
        List<Champion> championList = championRepository.findAll();
        for(Champion champion : championList) {
            championsDelays.put(champion.getName(), generateRandomDelay());
            championsPrices.put(champion.getName(), champion.getPrice());
        }
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void updateChampionsPrice() throws IOException {
        for(Map.Entry<String, Integer> championMap : championsDelays.entrySet()) {
            String championName = championMap.getKey();
            int delay = championMap.getValue();
            int currentPrice = championsPrices.get(championName);

            if(delay <= 0) {
                int changedPrice = currentPrice + generateRandomPrice(currentPrice);
                if(changedPrice < 0) changedPrice = 0;

                championsPrices.put(championName, changedPrice);
                championCustomDao.championPriceUpdate(championName, changedPrice);

                Champion champion = championRepository.findByName(championName);
                int champion_id = Math.toIntExact(champion.getId());

                ChampionPriceLog cpl = championPriceLogRepository.findByChampion_Id(champion_id);

                championPriceChangeLogRepository.save(
                        ChampionPriceChangeLog.builder()
                                .champion_id(champion_id)
                                .beforePrice(currentPrice)
                                .afterPrice(changedPrice)
                                .create_date(LocalDateTime.now())
                                .build()
                );

                ChampionPriceDto championPriceDto = ChampionPriceDto.builder()
                        .name(championName)
                        .price(champion.getPrice())
                        .percent(Math.round((changedPrice - currentPrice) * 100 / cpl.getPrice()))
                        .totalPrice(champion.getTotal_price())
                        .build();

                championsWS.changeChampionPrice(championPriceDto);

                championsDelays.put(championName, generateRandomDelay());
            }
            else {
                championsDelays.put(championName, delay - 1);
            }
        }
    }

    private int generateRandomPrice(int currentPrice) {
        return (currentPrice / 100) * (random.nextInt(11) - 5);
    }

    private int generateRandomDelay() {
        int minDelay = 5;
        int maxDelay = 3600;

        return minDelay + random.nextInt(maxDelay - minDelay + 1);
    }

}
