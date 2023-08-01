package com.example.lolchampionsinvestment.api.controller.main;

import com.example.lolchampionsinvestment.api.service.champion.ChampionService;
import com.example.lolchampionsinvestment.domain.champion.Champion;
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
        List<Champion> championList = championService.getMainChampionsView();

        mv.addObject("championList", championList);
        mv.setViewName("main/main.html");
        return mv;
    }

}
