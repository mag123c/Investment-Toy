package com.example.lolchampionsinvestment.domain.champion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {

    List<Champion> findAllByNameIn(List<String> name);
}
