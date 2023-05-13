package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import jdk.jshell.spi.ExecutionControl;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

/**
 * 게임 플레이어
 */
@Getter
public class Player {
    private Long userId;
    @Setter
    private AgricolaGame game;
    private boolean startingToken;
    private final EnumMap<ResourceType,Integer> resources;
    private final PlayerBoard playerBoard;
    private final List<Integer> cardHand;
    private final List<String> cardField;

    @Builder
    public Player(Long userId, boolean isStartPlayer){
        this.userId = userId;
        this.startingToken = isStartPlayer;
        this.resources = new EnumMap<>(ResourceType.class);
        for (ResourceType resource : ResourceType.values()){
            resources.put(resource,0);
        }
        this.playerBoard = PlayerBoard.builder().build();
        this.cardHand = new ArrayList<>();
        this.cardField = new ArrayList<>();
    }

    /**
     * increase family action's precondition
     * @return if player has empty room, return true
     */
    public boolean familyPrecondition(){
        return playerBoard.hasEmptyRoom();
    }

    /**
     * get player's resource
     * @param resourceType resource type to get
     * @return amount of resource
     */
    public int getResource(ResourceType resourceType){
        return resources.get(resourceType);
    }

    /**
     * add resources to storage
     * @param resource resource type and amout to add
     */
    public void addResource(ResourceStruct resource){
        resources.compute(resource.getResource(), (key, value) -> value + resource.getCount());
    }
    /**
     * add resources to storage
     * @param resourceType resource type to add
     * @param num amount of resource
     */
    public void addResource(ResourceType resourceType, int num){
        resources.compute(resourceType, (key, value) -> value + num);
    }

    /**
     * add resources to storage
     * @param resources List of a pair of resource and amount of resource
     */
    public void addResource(List<ResourceStruct> resources) {
        for (ResourceStruct resource : resources) {
            addResource(resource.getResource(), resource.getCount());
        }
    }

    /**
     * use resource
     * @param resourceType resource type to use
     * @param num amount of resource
     * @return Whether to use
     */
    public void useResource(ResourceType resourceType, int num){
        if (resources.get(resourceType) < num)
            throw new RuntimeException("자원이 부족합니다.");
        resources.compute(resourceType, (key, value) -> value - num);
    }

    /**
     * get player's resource
     * @param resourceType resource type to get
     * @return amount of resource
     */
    public ResourceStruct getResourceStruct(ResourceType resourceType){
        return ResourceStruct.builder()
                .resource(resourceType)
                .count(resources.get(resourceType))
                .build();
    }

    /**
     * 시작 토큰을 가지도록 설정한다.
     */
    public void setStartingTokenByTrue() {
        this.game.getPlayers().stream()
                .filter(player -> player.isStartingToken())
                .findAny()
                .orElseThrow(RuntimeException::new)
                .disableStartingToken();

        this.startingToken = true;
    }

    /**
     * 시작 토큰을 제거한다.
     */
    private void disableStartingToken() {
        this.startingToken = false;
    }

    /**
     * 현재 필드 단계를 반환한다.
     * @return
     */
    public FieldType getRoomType() {
        return playerBoard.getRoomType();
    }

    /**
     * 현재 게임 보드의 방 개수를 반환한다.
     * @return
     */
    public int getRoomCount() {
        return playerBoard.getRoomCount();
    }

    /**
     * 건축물 건설
     * @param y 건설할 가로축 좌표
     * @param x 건설할 세로축 좌표
     * @param fieldType 건설할 건물 종류
     * @return
     */
    public void buildField(int y, int x, FieldType fieldType){
        this.playerBoard.buildField(y, x, fieldType);
    }

    /** TODO
     * does player have cards of card type in hand
     * @param cardType card type
     * @return result
     */
    public boolean hasCardInHand(CardType cardType) {
        for (Integer id : cardHand){
            if (CardDictionary.cardDict.get(id).getCardType() == cardType)
                return true;
        }
        return false;
    }

    /** TODO
     * place card in hand to field. card becomes available.
     * @param cardID card ID
     * @return result
     */
    public boolean placeCard(String cardID){
        if(cardHand.remove(cardID)) {
            cardField.add(cardID);
            return true;
        }
        return false;
    }

    /** TODO
     * get major improvement card.
     * @param cardID card ID
     * @return result
     */
    public boolean getMajorCard(String cardID){
        if (!Objects.equals(CardDictionary.cardDict.get(cardID).getOwner(),"")) return false;
        cardField.add(cardID);
        CardDictionary.cardDict.get(cardID).setOwner(userId);
        return true;
    }

    /**
     * 모든 플레이어가 행동을 완했는지 확인
     * @return 만약 행동이 완료되었다면, true를 반환
     */
    public boolean isCompletedPlayed(){
        return playerBoard.isCompletedPlayed();
    }

    /**
     * 플레이 여부 초기화
     */
    public void initPlayed() {
        this.playerBoard.initPlayed();
    }

    /**
     * 아이 성장
     */
    public void growUpChild() {
        this.playerBoard.growUpChild();
    }

    /**
     * 수확 작업:
     * 플레이어 보드판에 있는 채소와 곡식 종자를 1개 수확한다.
     */
    public void harvest() {
        // 수확 산출물 계산
        List<ResourceStruct> outputs = this.playerBoard.harvest();

        // 플레이어 자원에 추가
        addResource(outputs);
    }

    /**
     * 동물 번식시키기:
     * 내 보드판에서 같은 종류의 동물이 2마리 이상 있는 경우 한 마리가 늘어난다(종류별 최대1마리).
     */
    public void breeding() {
        try {
            this.playerBoard.breeding();
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 먹여 살리기
     */
    public void feeding() {
        int foodNeeds = this.playerBoard.calculateFoodNeeds();
        if(getResourceStruct(ResourceType.FOOD).getCount() >= foodNeeds)
            useResource(ResourceType.FOOD, foodNeeds);
        else
            throw new RuntimeException("음식 토큰 부족");
    }

    /**
     * 방 업그레이드
     */
    public void upgradeRoom() {
        this.playerBoard.upgradeRoom();
    }

    /**
     * 아기를 입양한다.
     */
    public void addChild() {
        this.playerBoard.addChild();
    }

    /**
     * 가족 개수 반환
     */
    public int getFamilyNumber() {
        return this.playerBoard.getFamilyCount();
    }
}
