package com.example.lolchampionsinvestment.api.controller.champion;

import com.example.lolchampionsinvestment.api.service.champion.ChampionDataParsingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ChampionDataParsingController.class)
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
        //given
        List<Map<String, Object>> championList = List.of(
                Map.of("id", "ChampionAId", "name", "ChampionA", "description", "Champion A is a mage."),
                Map.of("id", "ChampionBId", "name", "ChampionB", "description", "Champion B is an assassin.")
        );

        //when //then
        mockMvc.perform(
                        post("/api/v1/champions/new")
                                .content(asJsonString(championList))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));

        verify(championDataParsingService).championsInsertTable();
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}