package com.semoss.agricola.GameRoomCommunication.service;

import com.semoss.agricola.GameRoom.Repository.GameRoomRepository;
import com.semoss.agricola.GameRoom.domain.GameRoom;
import com.semoss.agricola.GameRoom.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class DefaultCommunicationServiceTest {

    @Mock
    private GameRoomRepository gameRoomRepository;

    @InjectMocks
    private DefaultCommunicationService communicationService;

    GameRoom createGameRoom() {
        return GameRoom.builder()
            .name("test gameRoom")
            .capacity(4)
            .build();
    }

    User createUser(){
        return User.builder()
                .username("testUser")
                .build();
    }

    @Test
    public void givenGameRoomIdAndUsername_whenParticipate_thenUserIsAddedToGameRoom() {
        // given
        String username = "testUser";
        GameRoom gameRoom = createGameRoom();
        Long gameRoomId = gameRoom.getId();


        given(gameRoomRepository.findById(gameRoomId)).willReturn(Optional.of(gameRoom));

        // when
        User addedUser = communicationService.participate(gameRoomId, username);

        // then
        assertThat(gameRoom.getParticipants()).contains(addedUser);
    }

    @Test
    public void givenGameRoomIdAndUserId_whenExit_thenUserIsRemovedFromGameRoom() {
        // given
        GameRoom gameRoom = createGameRoom();
        User user = createUser();

        Long gameRoomId = gameRoom.getId();
        Long userId = user.getId();

        gameRoom.getParticipants().add(user);
        given(gameRoomRepository.findById(gameRoomId)).willReturn(Optional.of(gameRoom));

        // when
        communicationService.exit(gameRoomId, userId);

        // then
        assertThat(gameRoom.getParticipants()).doesNotContain(user);
    }
}
