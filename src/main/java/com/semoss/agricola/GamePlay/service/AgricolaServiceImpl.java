package com.semoss.agricola.GamePlay.service;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.PlayerResource;
import com.semoss.agricola.GamePlay.domain.gameBoard.GameBoard;
import com.semoss.agricola.GamePlay.domain.player.PlayerBoard;
import com.semoss.agricola.GameRoom.repository.GameRoomRepository;
import com.semoss.agricola.GameRoom.domain.GameRoom;
import com.semoss.agricola.GameRoom.domain.Game;
import com.semoss.agricola.GameRoomCommunication.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AgricolaServiceImpl implements AgricolaService {

    private final GameRoomRepository gameRoomRepository;

    /**
     * 플레이어 순서 변경
     * @param gameScripts
     */
    private void selectPlayerSequence(Game gameScripts) {
        //do nothing now

    }


    /**
     * 아그리콜라 게임 추출
     * @param gameRoomId
     * @return
     */
    private AgricolaGame extractGame(Long gameRoomId){
        // 라운드를 시작할 게임방 검색 및 적합성 검사
        GameRoom gameRoom =  gameRoomRepository.findById(gameRoomId).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 게임방이 존재하지 않습니다.")
        );
        if (!(gameRoom.getGame() instanceof AgricolaGame)){
            throw new RuntimeException("해당 게임방은 아그리콜라를 플레이하고 있지 않습니다.");
        }

        return (AgricolaGame) gameRoom.getGame();
    }

    /**
     * 플레이어 게임 객체 생성
     * @param users
     * @return
     */
    private List<Player> buildGamePlayer(List<User> users){
        // 플레이어 객체 생성
        List<Player> players = IntStream.range(0, users.size())
                .mapToObj(i -> {
                    boolean isFirst = i == 0;
                    return Player.builder()
                            .userId(users.get(i).getId())
                            .startingToken(isFirst)
                            .resources(PlayerResource.builder()
                                    .isStartingPlayer(isFirst)
                                    .build())
                            .playerBoard(PlayerBoard.builder().build())
                            .cardField(new ArrayList<>()) // TODO : 플레이어 카드 필드 구현
                            .cardHand(new ArrayList<>()) // TODO : 플레이어 직업 카드, 보조 설비 카드 분배 구현 (e,g 랜덤으로 7장 수령)
                            .build();
                })
                .toList();

        return players;
    }

    /**
     * 게임 시작 매커니즘
     * @param gameRoomId
     */
    @Override
    public void start(Long gameRoomId) {
        // 게임을 사작할 게임방 검색
        GameRoom gameRoom =  gameRoomRepository.findById(gameRoomId).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 게임방이 존재하지 않습니다.")
        );

        // TODO: 다인용 지원
        if (gameRoom.getParticipants().size() != 4){
            throw new RuntimeException("현재 4인용만 지원합니다.");
        }

        // 새로운 아그리콜라 게임 시스템을 제작한다.
        AgricolaGame game = AgricolaGame.builder()
                .gameBoard(GameBoard.builder().build()) // TODO: 게임 보드 설계
                .players(buildGamePlayer(gameRoom.getParticipants()))
                .build();

        // 현재 게임방에 아그리 콜라 게임 시스템 설정
        gameRoom.setGame(game);


        // Player 배치도 랜덤이다. & 선공 토큰을 랜덤으로 준다.
        selectPlayerSequence(gameRoom.getGame());
        List<Player> players = buildGamePlayer(gameRoom.getParticipants());

        // TODO: 주요설비가 나열이 된다.

        // TODO: 라운드카드가 같은 라운드 카드끼리 셔플한후 뒤집어진채로 세팅된다.

        // 게임 시작 후 최초 라운드 시작을 개시한다.
        roundStart(gameRoomId);
    }

    /**
     * 라운드 시작 매커니즘 : 게임 시작시 & 모든 플레이어 행동 종료시 실행
     [ROW TEXT: TODO 개선]
     * -보드판에 모인 공용 주요 설비 카드를 확인할 수 있다.
    - 내 보드판을 눌러 내가 가지고 있는 보조 설비 카드를 확인할 수 있다.
    - 내 보드판을 눌러 내가 가지고 있는 직업 카드를 확인할 수 있다.
    - 다른 사람의 판을 눌러 그 사람이 가지고 있는 보조 설비 카드와 직업 카드, 자원을 볼 수 있다.
    - 지금까지 게임의 진행사항(로그)을 확인할 수 있다.
    - 지금까지 게임의 플레이어 점수를 확인할 수 있다.
     * @param gameRoomId
     */
    private void roundStart(Long gameRoomId) {
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        // 이번 라운드의 행동이 공개한다.
        game.roundStart();

        // [직업,보조카드] 공개되는 라운드카드에 누적되어있는 자원을 자원을 배치한사람이 가져간다.

        // 자원 누적 칸의 경우 자원을 누적하고 누적되지않는 자원 칸의 경우 비어있는 칸의 자원만 보충한다.
    }

    /**
     * 플레이어 행동 + 언제나 가능한 행동(플레이어 턴에만 제약)
     * @param gameRoomId
     * @param userId
     * @param userActions
     */
    @Override
    public void playerAction(Long gameRoomId, Long userId, Object userActions) {
        //- 나의 차례가 끝나지 않았을 때 [주설비카드]를 이용하여 자원을 음식으로 교환할수있다.
        //- 나의 차례가 끝나지 않았을 때 [직업, 보조설비 카드]를 통해 추가 행동을 할 수 있다.
        //- 나의 차례가 끝나지 않았을 때 울타리 안에 있는 동물의 위치를 바꿀 수 있다.
        //- 가족이 올라가지 않은 칸에 가족을 한명 놓을 수 있다.
        //    - 행동칸에 가족을 놓으면 행동칸의 행동을 할 수 있다.
        //    - [직업,보조카드] 행동칸처럼 가족을 놓을 수 있으면 사용할 수 있다.
        //- 말을 놓은 후 무르기 버튼을 누르면 나의 행동이 취소되고 다시 돌아간다.
        //- 말을 놓은 후 확정 버튼을 누르면 나의 행동이 확정된다.
        //- 제한 시간안에 가족을 움직이지 않을 경우 더미보드판으로 가족이 이동한다.

        // 항상 할 수 있는거
        //- 보드판에 모인 공용 주요 설비 카드를 확인할 수 있다.
        //- 내 보드판을 눌러 내가 가지고 있는 보조 설비 카드를 확인할 수 있다.
        //- 내 보드판을 눌러 내가 가지고 있는 직업 카드를 확인할 수 있다.
        //- 다른 사람의 판을 눌러 그 사람이 가지고 있는 보조 설비 카드와 직업 카드, 자원을 볼 수 있다.
        //- 지금까지 게임의 진행사항(로그)을 확인할 수 있다.
        //- 지금까지 게임의 플레이어 점수를 확인할 수 있다.
    }

    /**
     * 라운드 종료 매커니즘
     * @param gameRoomId
     */
    private void roundEnd(Long gameRoomId) {
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        // 남은 말이 있는지 검증
        if(game.isAllPlayerPlayed() != false)
            throw new RuntimeException("아직 모둔 플레이를 하지 않은 플레이어가 존재합니다.");

        // 플레이어 행동 말 초기화
        game.initPlayerPlayed();

        // 수확 시기인 경우 수확 행동을 수행한다.
        int round = game.getRound();
        if (round == 4 || round == 7 || round == 9 || round == 11 || round == 13 || round == 14) {
            harvesting(gameRoomId);
        }

        // 아이를 어른으로 성장시킨다.
        game.growUpChild();
    }

    public void harvesting(Long gameRoomId) {
        //    - [수확 시기인 경우] 수확 시기가 되었을 때의 선공권을 가진 플레이어부터 순서대로 이하 **수확 행동**을 수행한다. (방 만들때 정한 시간의 2배)
        //        - 플레이어 보드판에 있는 채소와 곡식 종자를 1개 수확한다.
        //        - 사람 1명 당 두 개의 식량을 소비한다.  (아이의 경우 1개 소비한다.)
        //            - [전제조건] 식량이 부족한 경우, 내가 가지고 있는 자원을 통해 식량으로 교환할 수 있다.
        //            - 식량이 충분한 경우, 식량을 소비하고 종료한다.
        //            - 수확 시기에 사용할 수 있는 [직업 카드] 혹은 [설비 카드]가 있는 경우, 이 카드를 사용할 수 있다.
        //            - 식량이 부족한 상태에서 자원도 없거나 자원이 충분한데 교환하지 않는 경우 또는 시간초과가 됐을 경우 식량 1개 당 하나의 구걸 카드를 받는다.
        //        - 내 보드판에서 같은 종류의 동물이 2마리 이상 있는 경우 한 마리가 늘어난다(종류별 최대1마리).
    }

    /**
     * 게임 종료 매커니즘
     * @param gameRoomId
     */
    @Override
    public void finish(Long gameRoomId) {
        //1.
        //    - 플레이어가 소유한 자원에 따라 플레이어 점수가 확정되고 최종 순위가 확정된다.
        //    - ‘한 번 더 하기’ 버튼을 누르면 게임방으로 돌아간다.
        //    - ‘나가기’ 버튼을 누르면 게임로비창(게임방리스트있는곳)으로 나간다.
    }
}
