package com.semoss.agricola.GamePlay.service;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.CardDistributeStrategy;
import com.semoss.agricola.GamePlay.domain.GameProgress;
import com.semoss.agricola.GamePlay.domain.action.implement.DefaultAction;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.resource.AnimalType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import com.semoss.agricola.GameRoom.domain.GameRoom;
import com.semoss.agricola.GameRoom.domain.GameType;
import com.semoss.agricola.GameRoom.repository.GameRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AgricolaServiceImpl implements AgricolaService {

    private final GameRoomRepository gameRoomRepository;
    private final ObjectProvider<AgricolaGame> agricolaGameProvider;
    private final ObjectProvider<DefaultAction> actionProvider;
    private final ObjectProvider<CardDictionary> cardDictionaryProvider;

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
        if (gameRoom.getGame().getGameType() != GameType.Agricola){
            throw new RuntimeException("해당 게임방은 아그리콜라를 플레이하고 있지 않습니다.");
        }

        return (AgricolaGame) gameRoom.getGame();
    }

    /**
     * 게임 시작 매커니즘
     * @param gameRoomId 게임을 시작할 게임방 고유 식발자
     */
    @Override
    public void start(Long gameRoomId) {
        log.info("게임이 시작되었습니다.");
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
        List<DefaultAction> actions = actionProvider.stream().toList();
        CardDictionary cardDictionary = cardDictionaryProvider.getObject();
        AgricolaGame game = agricolaGameProvider.getObject(gameRoom.getParticipants(), "NONE", actions, cardDictionary);
        gameRoom.setGame(game);

        // 선공 플레이어의 경우 음식 토큰 2개, 아닌 경우 3개를 받는다.
        game.setUpStartingFood();

        // 각 플레이어에게 보조설비 및 직업카드를 7장 배포한다.
        game.distributeMinorCardsAndOccupations(CardDistributeStrategy.RANDOM);

        // 게임 시작 후 최초 라운드 시작을 개시한다.
        roundStart(gameRoomId);
    }

    /**
     * 라운드 시작 매커니즘 : 게임 시작시 & 모든 플레이어 행동 종료시 실행
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
        log.info("round가 시작되었습니다.: " + game.getGameState().getRound());

        // 현재 게임 상태를 선공 플레이어의 행동 단계로 변경한다.
        game.update(GameProgress.PlayerAction, game.getStartingPlayer());

        // 이번 라운드의 행동이 공개한다.
        game.increaseRound();

        // 자원 누적 칸의 경우 자원을 누적하고 누적되지않는 자원 칸의 경우 비어있는 칸의 자원만 보충한다.
        game.processStackEvent();

        // 현재 게임 상태를 선공 플레이어의 행동 단계로 변경한다.
        game.update(GameProgress.PlayerAction, game.getStartingPlayer());

    }

    /**
     * 플레이어 행동
     * @param gameRoomId
     */
    @Override
    public void playAction(Long gameRoomId, Long eventId, List<AgricolaActionRequest.ActionFormat> acts) {
        log.info("playAction 요청이 입력되었습니다. : " + eventId.toString());
        //- 나의 차례가 끝나지 않았을 때 [주설비카드]를 이용하여 자원을 음식으로 교환할수있다.
        //- 나의 차례가 끝나지 않았을 때 [직업, 보조설비 카드]를 통해 추가 행동을 할 수 있다.
        //- 나의 차례가 끝나지 않았을 때 울타리 안에 있는 동물의 위치를 바꿀 수 있다.
        //- 가족이 올라가지 않은 칸에 가족을 한명 놓을 수 있다.
        //    - 행동칸에 가족을 놓으면 행동칸의 행동을 할 수 있다.
        //    - [직업,보조카드] 행동칸처럼 가족을 놓을 수 있으면 사용할 수 있다.
        //- 말을 놓은 후 무르기 버튼을 누르면 나의 행동이 취소되고 다시 돌아간다.
        //- 말을 놓은 후 확정 버튼을 누르면 나의 행동이 확정된다.
        //- 제한 시간안에 가족을 움직이지 않을 경우 더미보드판으로 가족이 이동한다.

        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        // 해당 턴이 유효한지 검증
        if(game.getGameState().getGameProgress() != GameProgress.PlayerAction)
            throw new RuntimeException("게임이 액션을 수락하는 단계가 아닙니다.");

        // 행동 칸 작업 수행
        game.playAction(eventId, acts);

        // 모든 플레이어가 플레이를 마칠 경우 라운드 종료, 아닌 경우 다음 플레이어로 상태 변경
        Optional<Player> nextPlayer = game.findNextActionPlayer(game.getGameState().getPlayer());
        if(nextPlayer.isEmpty()){
            roundEnd(gameRoomId);
        } else{
            game.update(GameProgress.PlayerAction, nextPlayer.get());
        }
    }

    /**
     * 언제나 가능한 행동(플레이어 턴에만 제약)\
     * @param gameRoomId
     * @param improvementId
     * @param resource
     * @param count
     */
    @Override
    public void playExchange(Long gameRoomId, Long improvementId, ResourceType resource, Long count) {
        log.info("playExchange 요청이 입력되었습니다. : " + improvementId.toString());
        log.info(resource.getName());
        log.info(count);

        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        // 교환 작업 수행
        game.playExchange(improvementId, resource, count);

        // 만약 게임 상태가 수확단계에서 이루어진 교환이라면 이후에 수확 과정을 진행한다.
        if(game.getGameState().getGameProgress() == GameProgress.HARVEST){
            feeding(gameRoomId);
        }
    }

    /**
     * 언제나 가능한 행동(플레이어 턴에만 제약)\
     * @param gameRoomId
     * @param improvementId
     * @param animal
     * @param count
     */
    @Override
    public void playExchange(Long gameRoomId, Long improvementId, AnimalType animal, Long count) {
        log.info("playExchange 요청이 입력되었습니다. : " + improvementId.toString());
        log.info(animal.getName());
        log.info(count);

        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        // 교환 작업 수행
        game.playExchange(improvementId, animal, count);

        // 만약 게임 상태가 수확단계에서 이루어진 교환이라면 이후에 수확 과정을 진행한다.
        if(game.getGameState().getGameProgress() == GameProgress.HARVEST){
            feeding(gameRoomId);
        }
    }

    /**
     * 라운드 종료 매커니즘
     * @param gameRoomId
     */
    private void roundEnd(Long gameRoomId) {
        log.info("라운드가 종료되었습니다.");
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        // 플레이어 행동 말 초기화
        game.initPlayerPlayed();

        // 수확 시기인 경우 수확 행동을 수행한다.
        int round = game.getGameState().getRound();
        if (round == 4 || round == 7 || round == 9 || round == 11 || round == 13 || round == 14) {
            game.update(GameProgress.HARVEST, game.getStartingPlayer());
            harvesting(gameRoomId);
        } else {
            roundEndExtension(gameRoomId);
        }
    }

    private void roundEndExtension(Long gameRoomId) {
        log.info("라운드가 종료되었습니다. - Extension");
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        // 아이를 어른으로 성장시킨다.
        game.growUpChild();

        // 선공 플레이어를 변경한다.
        game.updateStartingPlayer();

        if(game.getGameState().getRound() < 14){
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
        log.info("수확 라운드입니다.");
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        AgricolaGame.GameState gameState = game.getGameState();

        Player player = gameState.getPlayer();

        // 수확
        game.harvest(player);

        // 먹여 살리기 작업
        feeding(gameRoomId);
    }

    /**
     * 먹여 살리기 단계
     * @param gameRoomId
     */
    public void feeding(Long gameRoomId) {
        log.info("먹여살리기 라운드입니다.");
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        AgricolaGame.GameState gameState = game.getGameState();

        Player player = gameState.getPlayer();

        // 먹여살리기
        player.feeding();

        breeding(gameRoomId);
    }

    /**
     * 번식 단계
     * @param gameRoomId
     */
    private void breeding(Long gameRoomId) {
        log.info("번식 라운드입니다.");
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        AgricolaGame.GameState gameState = game.getGameState();

        Player player = gameState.getPlayer();

        // 동물 번식
        player.breeding();

        // 다음 플레이어로 수확 상태 변경
        if(game.findNextPlayer(player).equals(game.getStartingPlayer())){
            roundEndExtension(gameRoomId);
        } else {
            game.update(GameProgress.HARVEST, game.findNextPlayer(player));
            harvesting(gameRoomId);
        }
    }

    /**
     * 게임 종료 매커니즘
     * @param gameRoomId
     */
    @Override
    public void finish(Long gameRoomId) {
        log.info("종료되었습니다.");

        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);
        game.update(GameProgress.FINISH, null);

        game.finish();
        //1.
        //    - 플레이어가 소유한 자원에 따라 플레이어 점수가 확정되고 최종 순위가 확정된다.
        //    - ‘한 번 더 하기’ 버튼을 누르면 게임방으로 돌아간다.
        //    - ‘나가기’ 버튼을 누르면 게임로비창(게임방리스트있는곳)으로 나간다.
    }

    @Override
    public boolean validatePlayer(Long gameRoomId, Object userId) {
        // 아그리콜라 게임 추출
        AgricolaGame game = extractGame(gameRoomId);

        return game.getGameState().getPlayer().getUserId() == userId;
    }
}
