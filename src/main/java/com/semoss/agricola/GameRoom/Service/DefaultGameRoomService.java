package com.semoss.agricola.GameRoom.Service;

import com.semoss.agricola.GameRoom.Repository.GameRoomRepository;
import com.semoss.agricola.GameRoom.domain.GameRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * 게임방의 생명주기에 관한 비지니스 객체
 */
@Service
@RequiredArgsConstructor
public class DefaultGameRoomService implements GameRoomService {

    private final GameRoomRepository gameRoomRepository;
    @Override
    public List<GameRoom> getAll() {
        //모든 게임룸을 가져온다.
        return gameRoomRepository.findAll();
    }

    @Override
    public GameRoom getOne(Long id) {
        //게임방이 존재할 경우 반환, 아닌 경우 에러 발생
        return gameRoomRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 게임방이 존재하지 않습니다.")
        );
    }

    @Override
    public void create(String name, int capacity) {
        //새로운 게임룸 생성
        GameRoom gameRoom = GameRoom.builder()
                .name(name)
                .capacity(capacity)
                .build();

        //새로운 게임룸 저장
        gameRoomRepository.insert(gameRoom);
    }

    @Override
    public void update(Long id, String name, Integer capacity){
        GameRoom gameRoom = getOne(id);
        if (name != null)
            gameRoom.setName(name);
        if (capacity != null)
            gameRoom.setCapacity(capacity);
    }

    @Override
    public void delete(Long id) {
        //기존 게임룸 삭제
        gameRoomRepository.deleteById(id);
    }
}
