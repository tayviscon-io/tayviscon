package ru.tayviscon.tayvisconrenderer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TayvisconRendererApplication {

    public static void main(String[] args) {
        SpringApplication.run(TayvisconRendererApplication.class, args);
    }

}
