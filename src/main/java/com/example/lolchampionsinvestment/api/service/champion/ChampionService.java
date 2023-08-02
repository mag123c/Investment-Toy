package com.example.lolchampionsinvestment.api.service.champion;

import com.example.lolchampionsinvestment.domain.champion.ChamiponPriceQueryRepository;
import com.example.lolchampionsinvestment.domain.champion.ChampionPriceDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChampionService {

    private final ChamiponPriceQueryRepository championPriceQueryRepository;

    public List<List<ChampionPriceDto>> getAllLatestChampions() {
        List<ChampionPriceDto> list = championPriceQueryRepository.findAllLatestChampions();
        int listSize = list.size();

        List<List<ChampionPriceDto>> groupingList = new ArrayList<>();
        for(int i = 0; i < listSize; i+=8) {
            int endIdx = Math.min(i + 8, listSize);
            groupingList.add(list.subList(i, endIdx));
        }

        return groupingList;
    }
}
