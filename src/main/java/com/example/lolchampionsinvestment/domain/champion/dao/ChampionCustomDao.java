package com.example.lolchampionsinvestment.domain.champion.dao;

import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import com.example.lolchampionsinvestment.domain.champion.domain.QChampion;
import com.example.lolchampionsinvestment.domain.champion.domain.QChampionPriceLog;
import com.example.lolchampionsinvestment.domain.champion.dto.ChampionMainViewDto;
import com.example.lolchampionsinvestment.domain.champion.dto.ChampionPriceDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.lolchampionsinvestment.domain.champion.domain.QChampion.champion;

@Repository
@RequiredArgsConstructor
public class ChampionCustomDao {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ChampionPriceDto> findAllLatestChampions() {
        QChampion C = champion;
        QChampionPriceLog CPL = QChampionPriceLog.championPriceLog;

        List<Tuple> tupleList = jpaQueryFactory.select(C.name, CPL.price, C.price)
                .from(CPL)
                .join(C)
                .on(CPL.champion_id.eq(C.id))
                .where(
                        Expressions.list(CPL.champion_id, CPL.create_date)
                                .in(
                                        jpaQueryFactory.select(
                                                        CPL.champion_id, CPL.create_date.max().as("create_date")
                                                )
                                                .from(CPL)
                                                .groupBy(CPL.champion_id)
                                )

                )
                .orderBy(CPL.champion_id.asc())
                .fetch();

        return tupleList.stream()
                .map(tuple -> {
                    String name = tuple.get(C.name);
                    int price = tuple.get(C.price);
                    int cplPrice = tuple.get(CPL.price);
                    int percent = Math.round((price - cplPrice) * 100 / cplPrice);
                    return new ChampionPriceDto(name, price, percent);
                })
                .collect(Collectors.toList());
    }

}
