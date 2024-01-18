package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;


@SpringBootApplication(scanBasePackages = {"org.example", "nghhng.common"}, exclude = {DataSourceAutoConfiguration.class})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableFeignClients
@EnableDiscoveryClient

public class BookingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);
        System.out.println("HELLO WORLD");
    }
}