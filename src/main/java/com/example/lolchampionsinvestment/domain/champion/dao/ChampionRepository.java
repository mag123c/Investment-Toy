package com.example.lolchampionsinvestment.domain.champion.dao;

import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {

    List<Champion> findAllByNameIn(List<String> name);
    List<Champion> findAllByOrderByNameAsc();

    Champion findByName(String name);
}
