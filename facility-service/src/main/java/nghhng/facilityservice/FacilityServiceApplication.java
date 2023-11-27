package nghhng.facilityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"nghhng.facilityservice", "tunght.toby.common"}, exclude = {DataSourceAutoConfiguration.class})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableFeignClients

public class FacilityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacilityServiceApplication.class, args);
		System.out.println("HELLO WORLD");
	}

}
