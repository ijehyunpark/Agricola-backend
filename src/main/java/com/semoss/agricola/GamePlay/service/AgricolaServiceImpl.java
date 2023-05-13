package com.semoss.agricola.GamePlay.service;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.GameProgress;
import com.semoss.agricola.GamePlay.domain.gameboard.GameBoard;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GameRoom.domain.GameRoom;
import com.semoss.agricola.GameRoom.repository.GameRoomRepository;
import com.semoss.agricola.GameRoomCommunication.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AgricolaServiceImpl implements AgricolaService {

    private final GameRoomRepository gameRoomRepository;

    /**
     * 아그리콜라 게임 추출
     * @param gameRoomId 게임을 찾을 게임방 고유 식별자
     * @return 게임방에서 진행 중인 아그리콜라 게임
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
     * 아그리콜라 플레이어 객체 리스트 생성
     * @param users 플레이어 객체를 만들 게임방 유저 객체
     * @param strategy TODO: 플레이어 객체를 만들 떄 사용할 전략(무작위 플레이어 순서 등)
     * @return 아그리콜라 플레이어 객체 리스트
     */
    private List<Player> buildGamePlayer(List<User> users, String strategy){
        // 플레이어 객체 생성
        List<Player> players = IntStream.range(0, users.size())
                .mapToObj(i -> {
                    boolean isFirst = i == 0;
                    return Player.builder()
                            .userId(users.get(i).getId())
                            .isStartPlayer(isFirst)
                            .build();
                })
                .toList();

        return players;
    }

    /**
     * 게임 시작 매커니즘
     * @param gameRoomId 게임을 시작할 게임방 고유 식발자
     */
    @Override
    public void start(Long gameRoomId) {
        // 게임을 사작할 게임방 검색
        GameRoom gameRoom =  gameRoomRepository.findById(gameRoomId).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 게임방이 존재하지 않습니다.")
        );

        if (gameRoom.getGame() != null)
            throw new RuntimeException("현재 진행중인 게임이 있습니다.");

        // TODO: 다인용 지원 혹은 제약 결정
//      if (gameRoom.getParticipants().size() != 4){
//          throw new RuntimeException("현재 4인용만 지원합니다.");
//      }

        // 새로운 아그리콜라 게임 시스템을 제작한다.
        AgricolaGame game = AgricolaGame.builder()
                .gameBoard(GameBoard.builder().build()) // TODO: 주요설비가 나열이 된다.
                .players(buildGamePlayer(gameRoom.getParticipants(), "NONE"))
                .build();

        // TODO: 개선필요, 플레이어가 소유자 게임을 알고 있어야 한다.
        for (Player player : game.getPlayers()){
            player.setGame(game);
        }

        // 현재 게임방에 아그리 콜라 게임 시스템 설정
        gameRoom.setGame(game);
        
        // 선공 플레이어의 경우 음식 토큰 2개, 아닌 경우 3개를 받는다.
        game.getPlayers().stream().forEach(
                player -> player.addResource(ResourceType.FOOD, game.getStartingPlayer() == player ? 2 : 3)
        );

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
     * @param gameRoomId 라운드 시작 프로세스를 진행할 게임방 고유 식별자
     */
    private void roundStart(Long gameRoomId) {
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        // [직업,보조카드] 공개되는 라운드카드에 누적되어있는 자원을 자원을 배치한사람이 가져간다.
        game.processReservationResource();

        // 자원 누적 칸의 경우 자원을 누적하고 누적되지않는 자원 칸의 경우 비어있는 칸의 자원만 보충한다.
        game.processStackEvent();

        // 현재 게임 상태를 선공 플레이어의 행동 단계로 변경한다.
        game.update(GameProgress.PlayerAction, game.getStartingPlayer().getUserId());

        // 이번 라운드의 행동이 공개한다.
        game.increaseRound();
    }

    /**
     * 플레이어 행동
     * @param gameRoomId
     * @param userActions
     */
    @Override
    public void playAction(Long gameRoomId, Object userActions) {
        //- 나의 차례가 끝나지 않았을 때 [주설비카드]를 이용하여 자원을 음식으로 교환할수있다.
        //- 나의 차례가 끝나지 않았을 때 [직업, 보조설비 카드]를 통해 추가 행동을 할 수 있다.
        //- 나의 차례가 끝나지 않았을 때 울타리 안에 있는 동물의 위치를 바꿀 수 있다.
        //- 가족이 올라가지 않은 칸에 가족을 한명 놓을 수 있다.
        //    - 행동칸에 가족을 놓으면 행동칸의 행동을 할 수 있다.
        //    - [직업,보조카드] 행동칸처럼 가족을 놓을 수 있으면 사용할 수 있다.
        //- 말을 놓은 후 무르기 버튼을 누르면 나의 행동이 취소되고 다시 돌아간다.
        //- 말을 놓은 후 확정 버튼을 누르면 나의 행동이 확정된다.
        //- 제한 시간안에 가족을 움직이지 않을 경우 더미보드판으로 가족이 이동한다.

    }

    /**
     * 언제나 가능한 행동(플레이어 턴에만 제약)
     * @param gameRoomId
     * @param userActions
     */
    @Override
    public void playExchange(Long gameRoomId, Object userActions) {
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
        if(!game.isAllPlayerPlayed()) {
            throw new RuntimeException("아직 모둔 플레이를 하지 않은 플레이어가 존재합니다.");
        }

        // 플레이어 행동 말 초기화
        game.initPlayerPlayed();

        // 수확 시기인 경우 수확 행동을 수행한다.
        int round = game.getRound();
        if (round == 4 || round == 7 || round == 9 || round == 11 || round == 13 || round == 14) {
            game.update(GameProgress.HARVEST, game.getStartingPlayer().getUserId());
            harvesting(gameRoomId);
        }
    }

    private void roundEndExtension(Long gameRoomId) {
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);


        // 아이를 어른으로 성장시킨다.
        game.growUpChild();

        // 선공 플레이어를 변경한다.
        game.updateStartingPlayer();

        if(game.getRound() < 14){
            // 새로운 라운드를 시작한다.
            roundStart(gameRoomId);
        } else {
            // 게임을 종료한다.
            finish(gameRoomId);
        }
    }

    /**
     * 수확 단계
     * @param gameRoomId
     */
    public void harvesting(Long gameRoomId) {
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        AgricolaGame.GameState gameState = game.getGameState();

        Player player = game.findPlayerByUserId(gameState.getUserId());

        // 수확
        player.harvest();

        // 먹여 살리기 작업
        feeding(gameRoomId);

        // 동물 번식
        player.breeding();

        // 다음 플레이어로 수확 상태 변경
        if(game.getNextPlayer().equals(game.getStartingPlayer())){
            roundEndExtension(gameRoomId);
        } else {
            game.update(GameProgress.HARVEST, game.getNextPlayer().getUserId());
            harvesting(gameRoomId);
        }

    }

    /**
     * 먹여 살리기 단계
     * @param gameRoomId
     */
    public void feeding(Long gameRoomId) {
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        AgricolaGame.GameState gameState = game.getGameState();

        Player player = game.findPlayerByUserId(gameState.getUserId());

        player.feeding();
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

    @Override
    public boolean validatePlayer(Long gameRoomId, Object userId) {
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        return game.getGameState().getUserId() == userId;
    }
}
