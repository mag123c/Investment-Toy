package com.example.lolchampionsinvestment.api.controller.main;

import com.example.lolchampionsinvestment.api.service.champion.ChampionService;
import com.example.lolchampionsinvestment.domain.champion.ChampionPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class MainViewController {

    @Autowired
    ChampionService championService;

    @GetMapping("/")
    public ModelAndView mainView() {
        ModelAndView mv = new ModelAndView();
        List<List<ChampionPriceDto>> groupingList = championService.getAllLatestChampions();

        mv.addObject("groupingList", groupingList);
        mv.setViewName("main/main.html");
        return mv;
    }

}
