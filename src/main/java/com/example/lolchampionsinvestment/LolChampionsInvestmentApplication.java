package com.example.lolchampionsinvestment;

import com.example.lolchampionsinvestment.api.service.champion.ChampionDataParsingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LolChampionsInvestmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(LolChampionsInvestmentApplication.class, args);
	}

}
