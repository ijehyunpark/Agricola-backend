package com.semoss.agricola.service;

import com.semoss.agricola.domain.GameRoom;
import com.semoss.agricola.domain.User;
import com.semoss.agricola.dto.GameRoomCreateRequest;
import com.semoss.agricola.dto.GameRoomResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GameRoomService {
    private final List<GameRoom> memoryGameRoomList = new ArrayList<>();

    /**
     * game room 존재 여부 확인
     * @param gameRoomId 확인할 game room의 고유 식별자
     * @return 해당 game room이 존재할 경우 true, 아닐 경우 false
     */
    public boolean existGameRoom(Long gameRoomId) {
        return memoryGameRoomList.stream()
                .anyMatch(gameRoom -> gameRoom.getId() == gameRoomId);
    }

    /**
     * game room을 가져온다.
     * @param gameRoomId 해당 game room의 고유 식별자
     * @return 해당 game room 인스턴스
     */
    public Optional<GameRoom> getGameRoom(Long gameRoomId) {
        return memoryGameRoomList.stream()
                .filter(gameRoom -> gameRoom.getId() == gameRoomId)
                .findAny();
    }

    /**
     * game room에 참여중인 유저를 가져온다.
     * @param gameRoomId game room의 고유 식별자
     * @param userId 가져올 참여자의 고유 식별자
     * @return 해당 참여자 인스턴스
     * @throws NoSuchElementException 해당 userId를 가진 참여자가 없을 경우 발생한다.
     */
    public Optional<User> getUser(Long gameRoomId, Long userId) throws NoSuchElementException{
        GameRoom gameRoom = getGameRoom(gameRoomId).orElseThrow(NoSuchElementException::new);

        return gameRoom.getParticipants().stream()
                .filter(user -> user.getId() == userId)
                .findAny();
    }

    /**
     * game room에 참여중인 유저를 제거한다.
     * @param gameRoomId game room의 고유 식별자
     * @param userId 제거할 참여자의 고유 식별자
     */
    public void deleteUser(Long gameRoomId, Long userId) throws NoSuchElementException {
        GameRoom gameRoom = getGameRoom(gameRoomId).orElseThrow(NoSuchElementException::new);

        gameRoom.getParticipants().removeIf(user -> user.getId() == userId);
    }

    /**
     * 모든 GameRoom을 반환한다.
     * @return GameRoon을 response 객체에 담아 리스트로 반환한다.
     */
    public List<GameRoomResponse> getGameRoomList(){
        return memoryGameRoomList.stream()
                .map(GameRoomResponse::toDto)
                .toList();
    }

    /**
     * 새로운 gameRoom을 생성한다.
     * @param gameRoomCreateRequest gameRoom 생성에 필요한 요소를 가지고 있다.
     */
    public void createGameRoom(GameRoomCreateRequest gameRoomCreateRequest) {
        GameRoom newGameRoom = GameRoom.builder()
                .name(gameRoomCreateRequest.getName())
                .capacity(gameRoomCreateRequest.getCapacity())
                .build();

        memoryGameRoomList.add(newGameRoom);
    }

    /**
     * 기존 gameRoon을 제거한다.
     * @param id 제거할 gameRoon의 고유 식별자
     */
    public void destroyGameRoom(Long id) {
        memoryGameRoomList.removeIf(gameRoom -> gameRoom.getId() == id);
    }
}
