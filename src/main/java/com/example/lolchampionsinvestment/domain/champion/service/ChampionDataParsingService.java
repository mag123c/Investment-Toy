package com.example.lolchampionsinvestment.domain.champion.service;

import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChampionDataParsingService {

    private final ChampionRepository championRepository;
    private static String path = "/static/json/championFull.json";
    private static String imgURL = "http://ddragon.leagueoflegends.com/cdn/13.14.1/img/champion/";

    public void championsInsertTable() {
        List<Map<String, Object>> championList = championsMapping();
        List<Champion> champions = new ArrayList<Champion>();

        for(Map<String, Object> champion : championList) {
            champions.add(championPriceSettings(champion));
        }

        championRepository.saveAll(champions);
    }

    private Champion championPriceSettings(Map<String, Object> map) {
        return Champion.builder()
                .name((String) map.get("name"))
                .eng_name((String) map.get("id"))
                .price((int) (Math.random() * 9 + 1) * 1000)
                .img(imgURL + (String) map.get("id") + ".png")
                .description((String) map.get("description"))
                .createDateTime(LocalDateTime.now())
                .build();
    }

    public List<Map<String, Object>> championsMapping() {
        List<Map<String, Object>> championList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String championJsonString = getChampions();
            Map<String, Object> championsMap = objectMapper.readValue(championJsonString, new TypeReference<Map<String, Object>>() {});
            Map<String, Map<String, Object>> championMap = (Map<String, Map<String, Object>>) championsMap.get("data");

            championList = championMap.values().stream().map(championData -> Map.of(
                    "id", championData.get("id"),
                    "name", championData.get("name"),
                    "description", championData.get("lore")
            )).toList();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return championList;
    }

    private String getChampions() {
        return readLines();
    }

    private String readLines() {
        return new BufferedReader(
                new InputStreamReader(
                        ChampionDataParsingService.class.getResourceAsStream(path)
                )
        ).lines().collect(Collectors.joining());
    }

}
