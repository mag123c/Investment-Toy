package com.example.lolchampionsinvestment.api;

import com.example.lolchampionsinvestment.domain.champion.service.ChampionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TradeViewController {

    @Autowired
    ChampionService championService;

    @GetMapping("/api/v1/champions")
    public ModelAndView getTradingViewMain() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("allChampion", championService.getAllLatestChampions());
        mv.setViewName("trading/trading.html");
        return mv;
    }
}
