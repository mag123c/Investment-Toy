package com.example.lolchampionsinvestment.api.service.champion;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionCustomDao;
import com.example.lolchampionsinvestment.domain.champion.dto.ChampionPriceDto;
import com.example.lolchampionsinvestment.domain.champion.service.ChampionDataParsingService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@SpringBootTest
@Transactional
public class ChampionPriceQueryRepositoryTest {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    ChampionCustomDao championPriceQueryRepository;
    @Autowired
    ChampionDataParsingService championDataParsingService;

    @BeforeEach
    void init() {
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @DisplayName("챔피언 별 가장 최근 가격을 가져온다.")
    @Test
    void getChampionsLatestPrice(){
        // given
        List<ChampionPriceDto> championPriceDtoList = championPriceQueryRepository.findAllLatestChampions();

        // when // then
        assertThat(championPriceDtoList.size()).isEqualTo(164);

        assertThat(championPriceDtoList)
                .extracting("name", "price", "percent")
                .containsAnyOf(
                        tuple("아트록스", 7000, -36),
                        tuple("아리", 2000, 0),
                        tuple("카이사", 8000, 0)
                );
    }

}
