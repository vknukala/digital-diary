package com.github.vknukala.digitaldiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/*@OpenAPIDefinition(
        info = @Info(
                title = "Digital Diary API",
                version = "v0.0.1",
                description = "This is a digital diary API for creating/updating users and " +
                        "storing/editing user's thoughts, pictures ."))*/
@SpringBootApplication
@ConfigurationPropertiesScan("com.github.vknukala.digitaldiary.config")
public class DigitalDiaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalDiaryApplication.class, args);
    }


}
