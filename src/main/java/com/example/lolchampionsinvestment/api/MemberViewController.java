package com.example.lolchampionsinvestment.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberViewController {

    @GetMapping("/member/signin")
    public String loginView() {
        return "member/signin.html";
    }

}
