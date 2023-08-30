package com.example.lolchampionsinvestment.domain.champion.service;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionCustomDao;
import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import com.example.lolchampionsinvestment.domain.champion.dto.ChampionMainViewDto;
import com.example.lolchampionsinvestment.domain.champion.dto.ChampionPriceDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChampionService {

    private final ChampionCustomDao championCustomDao;

    private final ChampionRepository championRepository;

    public List<ChampionMainViewDto> getAllChampionMainViewDto() {
        List<Champion> champions = championRepository.findAllByOrderByNameAsc();

        return champions.stream()
                .map(champion -> {
                    String name = champion.getName();
                    String img = "http://ddragon.leagueoflegends.com/cdn/img/champion/loading/" + champion.getEng_name() + "_0.jpg";
                    return new ChampionMainViewDto(name, img);
                })
                .collect(Collectors.toList());
    }

    public List<ChampionPriceDto> getAllLatestChampions() {
        return championCustomDao.findAllLatestChampions();
    }

}
