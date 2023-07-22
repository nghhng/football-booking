package nghhng.footballbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FootballBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballBookingApplication.class, args);
		System.out.println("HELLO WORLD");
	}

}
