package com.example.lolchampionsinvestment.domain.champion.service;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionCustomDao;
import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ChampionRandomUpdaterTest {

    @Autowired
    ChampionRandomUpdater championRandomUpdater;

    @Autowired
    ChampionRepository championRepository;

    @Autowired
    ChampionCustomDao championCustomDao;

    @Autowired
    ChampionDataParsingService championDataParsingService;

    @Autowired
    EntityManager em;

    private Random random = new Random();

    @DisplayName("챔피언 데이터를 입력하고, 입력된 챔피언의 가격이 랜덤으로 2건 먼저 변경된 데이터가 존재할 경우 종료한다.")
    @Test
    void championPriceChangeRandom() throws InterruptedException {
        //given
        championDataParsingService.championsInsertTable();

        Map<String, Long> championsDelays = new HashMap<>();
        Map<String, Integer> championsPrices = new HashMap<>();

        List<Champion> championList = championRepository.findAll();
        for (Champion champion : championList) {
            championsDelays.put(champion.getName(), generateRandomDelay());
            championsPrices.put(champion.getName(), champion.getPrice());
        }

        //when
        int changeCnt = 0;
        Map<String, Integer> championsChangedCheckMap = new HashMap<>();
        while(changeCnt < 2) {
            for (Map.Entry<String, Long> championMap : championsDelays.entrySet()) {
                if(changeCnt > 2) break;

                String championName = championMap.getKey();
                long delay = championMap.getValue();
                int currentPrice = championsPrices.get(championName);

                if (delay <= 0) {
                    int changedPrice = currentPrice + generateRandomPrice(currentPrice);
                    championsPrices.put(championName, changedPrice);
                    championCustomDao.championPriceUpdate(championName, changedPrice);
                    changeCnt++;

                    championsChangedCheckMap.put(championName, changedPrice);
                    championsDelays.put(championName, generateRandomDelay());
                } else {
                    championsDelays.put(championName, delay - 1000);
                }
            }

            Thread.sleep(1000);
        }

        //then
        System.out.println(championsChangedCheckMap.size());
        assertThat(championsChangedCheckMap).isNotEmpty();

        for(String key : championsChangedCheckMap.keySet()) {
            Champion champion = championRepository.findByName(key);
            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
            System.out.println(champion.getName());
            System.out.println(champion.getPrice());
            System.out.println(championsChangedCheckMap.get(champion.getName()));
            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
            assertThat(championsChangedCheckMap.get(key)).isEqualTo(champion.getPrice());
        }
    }

    private int generateRandomPrice(int currentPrice) {
        return (currentPrice / 100) * (random.nextInt(11) - 5);
    }

    private long generateRandomDelay() {
        long minDelayMillis = 5000;
        long maxDelayMillis = 3600000;

        return minDelayMillis + random.nextLong() % (maxDelayMillis - minDelayMillis + 1);
    }
}