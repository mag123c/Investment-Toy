package com.example.lolchampionsinvestment.api.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewController {

    @GetMapping("/")
    public String mainView() {
        return "main/main.html";
    }

}
