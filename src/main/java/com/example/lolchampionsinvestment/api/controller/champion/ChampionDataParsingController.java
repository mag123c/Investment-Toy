package com.example.lolchampionsinvestment.api.controller.champion;

import com.example.lolchampionsinvestment.api.ApiResponse;
import com.example.lolchampionsinvestment.api.service.champion.ChampionDataParsingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
public class ChampionDataParsingController {

    private final ChampionDataParsingService championDataParsingService;

    @PostMapping("/api/v1/champions/new")
    public ApiResponse<String> addAllJsonChampionsData() {
        try {
            championDataParsingService.championsInsertTable();
            return ApiResponse.ok("파싱 및 테이블 저장 완료");
        } catch (HttpClientErrorException httpClientErrorException) {
            return ApiResponse.of(HttpStatus.BAD_REQUEST, httpClientErrorException.getMessage(), null);
        } catch (Exception e) {
            return ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while parsing and saving champion data", null);
        }
    }

}
