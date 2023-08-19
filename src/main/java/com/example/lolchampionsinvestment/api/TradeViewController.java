package com.example.lolchampionsinvestment.api;

import com.example.lolchampionsinvestment.domain.champion.service.ChampionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TradeViewController {

    @Autowired
    ChampionService championService;

    @GetMapping("/champions")
    public ModelAndView getTradingViewMain(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        if(session.getAttribute("UserName") == null) {
            mv.setViewName("redirect:/member/signin");
        }
        else {
            mv.addObject("allChampion", championService.getAllLatestChampions());
            mv.setViewName("trading/trading.html");
        }
        return mv;
    }
}
