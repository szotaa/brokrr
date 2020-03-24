package pl.szotaa.brokrr.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BrokrrServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(BrokrrServerApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
