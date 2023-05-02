package com.semoss.agricola.GameRoom.config;

import com.semoss.agricola.GameRoom.Repository.MemoryGameRoomRepository;
import com.semoss.agricola.GameRoom.Service.DefaultGameRoomService;
import com.semoss.agricola.GameRoom.Service.GameRoomService;
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
}
