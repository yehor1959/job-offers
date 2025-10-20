package com.jobOffers.jobOffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableConfigurationProperties({WinningNumbersGeneratorFacadeConfigurationProperties .class})
//@EnableScheduling
public class JobOffersApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobOffersApplication.class, args);
	}

}
