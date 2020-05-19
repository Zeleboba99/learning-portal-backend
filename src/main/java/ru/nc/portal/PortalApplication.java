package ru.nc.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.nc.portal.config.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class PortalApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "production");
        SpringApplication.run(PortalApplication.class, args); }

}
