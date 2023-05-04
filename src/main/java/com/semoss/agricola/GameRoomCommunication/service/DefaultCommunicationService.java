package com.semoss.agricola.GameRoomCommunication.service;

import com.semoss.agricola.GameRoom.Repository.GameRoomRepository;
import com.semoss.agricola.GameRoom.domain.GameRoom;
import com.semoss.agricola.GameRoom.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DefaultCommunicationService implements CommunicationService {

    private final GameRoomRepository gameRoomRepository;

    @Override
    public User participate(Long gameRoomId, String username) {
        // 해당 게임방 검색
        GameRoom gameRoom = gameRoomRepository.findById(gameRoomId).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 게임방이 존재하지 않습니다.")
        );

        // 현재 게임방에 유저 추가
        return gameRoom.participate(username);
    }

    @Override
    public User exit(Long gameRoomId, Long userId) {
        // 해당 게임방 검색
        GameRoom gameRoom = gameRoomRepository.findById(gameRoomId).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 게임방이 존재하지 않습니다.")
        );

        // 게임방에서 유저를 제거한다.
        gameRoom.exit(userId);
        return null;
    }

    @Override
    public void start(Long gameRoomId) {
        //doNothing now
    }
}
