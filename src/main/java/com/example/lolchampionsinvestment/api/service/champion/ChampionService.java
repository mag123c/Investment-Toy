package com.example.lolchampionsinvestment.api.service.champion;

import com.example.lolchampionsinvestment.domain.champion.Champion;
import com.example.lolchampionsinvestment.domain.champion.ChampionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChampionService {

    private final ChampionRepository championRepository;

    public List<Champion> getMainChampionsView() {
        List<Champion> list = championRepository.findAll();
        return list;
    }
}
