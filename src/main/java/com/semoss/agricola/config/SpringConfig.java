package com.semoss.agricola.config;

import com.semoss.agricola.GamePlay.service.AgricolaServiceImpl;
import com.semoss.agricola.GameRoom.repository.MemoryGameRoomRepository;
import com.semoss.agricola.GameRoom.service.DefaultGameRoomService;
import com.semoss.agricola.GameRoom.service.GameRoomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public MemoryGameRoomRepository gameRoomRepository() {
        return new MemoryGameRoomRepository();
    }

    @Bean
    public GameRoomService gameRoomService() {
        return new DefaultGameRoomService(gameRoomRepository());
    }

    @Bean
    public AgricolaServiceImpl agricolaService() {
        return new AgricolaServiceImpl(gameRoomRepository());
    }
}
