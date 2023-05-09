package com.semoss.agricola.GameRoom.domain;

import com.semoss.agricola.GameRoomCommunication.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 게임의 전제조건과 진행사항을 관리할 컨테이너
 */
@Getter
public class GameRoom {
    private static long NextGameRoomID = 1L;
    private final Long id;
    @Setter
    private String name;
    private int capacity;
    private final List<User> participants;

    @Setter
    private Game game;

    @Builder
    public GameRoom(String name, int capacity) {
        this.id = NextGameRoomID++;
        this.name = name;
        this.capacity = capacity;
        this.participants = new ArrayList<>();
    }

    public void setCapacity(int capacity) {
        if (this.participants.size() > capacity)
            throw new IllegalStateException("현재 참여자 수가 설정한 최대 참여자 수보다 많습니다.");
        this.capacity = capacity;
    }

    /**
     * 게임방에 참여할 새로운 유저를 추가한다.
     * @param username 추가할 유저가 사용할 이름
     * @return 참여한 유저 객체
     */
    public User participate(String username) {
        // 현재 참여 인원 개수 검증
        if(this.getParticipants().size() >= this.getCapacity()) {
            throw new RuntimeException("참여 인원이 가득찼습니다.");
        }

        // 새로운 유저 생성
        User user = User.builder()
                .username(username)
                .build();

        // 새로운 유저 추가
        this.participants.add(user);

        return user;
    }

    /**
     * 현재 참여중인 특정 유저를 제거한다.
     * @param userId 제거할 유저의 고유 식별자
     */
    public void exit(Long userId) {
        this.participants.removeIf(user -> user.getId().equals(userId));
    }
}
