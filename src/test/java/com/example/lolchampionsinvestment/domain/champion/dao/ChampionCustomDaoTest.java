package com.example.lolchampionsinvestment.domain.champion;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionCustomDao;
import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.dto.ChampionPriceDto;
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

@SpringBootTest
@Transactional
public class ChampionCustomDaoTest {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    ChampionCustomDao championCustomDao;
    @Autowired
    ChampionRepository championRepository;

    @BeforeEach
    void init() {
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @DisplayName("챔피언 별 가장 최근 가격을 가져온다.")
    @Test
    void getChampionsLatestPrice(){
        // given
        List<ChampionPriceDto> championPriceDtoList = championCustomDao.findAllLatestChampions();

        // when // then
        assertThat(championPriceDtoList.size()).isEqualTo(164);
        assertThat(championPriceDtoList.size()).isEqualTo(championRepository.findAll().size());
    }

}
