package com.example.lolchampionsinvestment.domain.champion.controller;

import com.example.lolchampionsinvestment.domain.champion.service.ChampionDataParsingService;
import com.example.lolchampionsinvestment.domain.champion.controller.ChampionDataParsingController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ChampionDataParsingController.class)
@ActiveProfiles("test")
class ChampionDataParsingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private ChampionDataParsingService championDataParsingService;

    @DisplayName("챔피언 데이터를 파싱하고 테이블에 등록한다.")
    @Test
    void testChampionDataParsingAndInsertion() throws Exception {
        //when //then
        mockMvc.perform(
                        post("/api/v1/champions/new")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));

        verify(championDataParsingService).championsInsertTable();
    }

}