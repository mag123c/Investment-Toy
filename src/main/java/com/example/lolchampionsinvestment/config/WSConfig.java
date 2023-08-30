package com.example.lolchampionsinvestment.config;

import com.example.lolchampionsinvestment.domain.trading.handler.ChampionsWS;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WSConfig implements WebSocketConfigurer{

    private final ChampionsWS championsWS;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(championsWS, "/championsAll").setAllowedOriginPatterns("*").withSockJS();
    }

}