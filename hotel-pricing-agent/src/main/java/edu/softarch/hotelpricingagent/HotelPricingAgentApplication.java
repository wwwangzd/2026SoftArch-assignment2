package edu.softarch.hotelpricingagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HotelPricingAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelPricingAgentApplication.class, args);
	}

}
