package com.semoss.agricola.config;

import com.semoss.agricola.repository.MemoryGameRoomRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemoryGameRoomRepository gameRoomRepository() {
        return new MemoryGameRoomRepository();
    }
}
