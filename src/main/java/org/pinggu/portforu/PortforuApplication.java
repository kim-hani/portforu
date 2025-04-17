package org.pinggu.portforu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PortforuApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortforuApplication.class, args);
    }

}
