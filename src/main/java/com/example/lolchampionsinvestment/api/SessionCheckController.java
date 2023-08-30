package com.example.lolchampionsinvestment.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
public class SessionCheckController {

    @GetMapping("/api/v1/sessions")
    public ResponseEntity<String> checkSessionGoTradingView(@SessionAttribute(name = "nickname", required = false) String nickname) {
        if (nickname == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired or not logged in.");
        } else {
            return ResponseEntity.ok("Session is valid.");
        }
    }
}
