package com.example.lolchampionsinvestment.api;

import com.example.lolchampionsinvestment.domain.champion.service.ChampionService;
import com.example.lolchampionsinvestment.domain.champion.dto.ChampionMainViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainViewController {

    @Autowired
    ChampionService championService;

    @GetMapping("/")
    public ModelAndView mainView() {
        ModelAndView mv = new ModelAndView();
//        List<List<ChampionPriceDto>> groupingList = championService.getAllLatestChampions();
        List<ChampionMainViewDto> championMainViewDtos = championService.getAllChampionMainViewDto();

        mv.addObject("championMainViewDtos", championMainViewDtos);
        mv.setViewName("main/main.html");
        return mv;
    }

}
