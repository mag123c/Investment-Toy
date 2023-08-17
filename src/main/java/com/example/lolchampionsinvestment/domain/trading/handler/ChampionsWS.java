package com.example.lolchampionsinvestment.domain.trading.handler;

import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import com.example.lolchampionsinvestment.domain.champion.dto.ChampionPriceDto;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ServerEndpoint(value = "/championsAll")
@Component
public class ChampionsWS extends TextWebSocketHandler {

    private static List<WebSocketSession> USER = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        USER.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        USER.remove(session);
    }

    public void changeChampionPrice(ChampionPriceDto championPriceDto) throws IOException {
        for(WebSocketSession user : USER) {
            championPriceDto.getName();
            championPriceDto.getPrice();
            championPriceDto.getTotalPrice();
            championPriceDto.getPercent();
            user.sendMessage(new TextMessage("d"));
        }
    }

}
