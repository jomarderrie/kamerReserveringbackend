package com.example.taskworklife.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Configuration
@ConfigurationProperties("reservering")
@Data
public class ReserveringConfiguration {
    String uploadPath;

    String profileImagesFolder = "profile";

    String kamerFolder = "kamer";

    public String getFullProfileImagesPath() {
        return this.uploadPath + "/" + this.profileImagesFolder;
    }

    public String getKamerFolder() {
        return this.uploadPath + "/" + this.kamerFolder;
    }
}
