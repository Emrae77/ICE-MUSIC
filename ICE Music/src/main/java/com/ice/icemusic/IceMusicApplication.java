package com.ice.icemusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IceMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(IceMusicApplication.class, args);
    }

}
