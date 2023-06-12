package nghhng.facilityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableFeignClients public class FacilityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacilityServiceApplication.class, args);
		System.out.println("HELLO WORLD");
	}

}
