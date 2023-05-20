package com.semoss.agricola.GamePlay.domain.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 게임 플레이어
 */
@Getter
@NoArgsConstructor
public class Player {
    private Long userId;
    @JsonIgnore
    private AgricolaGame game;
    private boolean startingToken;
    private final EnumMap<ResourceType,Integer> resources = new EnumMap<>(ResourceType.class);
    private final PlayerBoard playerBoard = PlayerBoard.builder().build();
    private final List<Long> cardHand = new ArrayList<>();
    private final List<Long> cardField = new ArrayList<>();

    @Builder
    public Player(Long userId, AgricolaGame game, boolean isStartPlayer){
        this.userId = userId;
        this.game = game;
        this.startingToken = isStartPlayer;
        for (ResourceType resource : ResourceType.values()){
            resources.put(resource,0);
        }
    }

    /**
     * 빈 방 존재 여부 확인
     * @return 만약 플레이어가 빈 방을 가지고 있다면 true를 반환한다.아닌 경우 false를 반환한다.
     */
    public boolean existEmptyRoom(){
        return playerBoard.existEmptyRoom();
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
     */
    public void useResource(ResourceType resourceType, int num){
        if (resources.get(resourceType) < num)
            throw new RuntimeException("자원이 부족합니다.");
        resources.compute(resourceType, (key, value) -> value - num);
    }

    /**
     * use resource
     * @param resource resource type and amount to use
     */
    public void useResource(ResourceStruct resource){
        useResource(resource.getResource(), resource.getCount());
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
    public RoomType getRoomType() {
        return playerBoard.getRoomType();
    }

    /**
     * 현재 게임 보드의 방 개수를 반환한다.
     * @return
     */
    @JsonIgnore
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
     * 플레이어의 핸드에 해당 타입의 카드가 있는지 확인
     * @param cardType card type
     * @return result
     */
    public boolean hasCardInHand(CardType cardType) {
        for (Long id : cardHand){
            if (game.getCardDictionary().cardDict.get(id).getCardType() == cardType)
                return true;
        }
        return false;
    }

    /**
     * 해당 ID의 카드를 필드에 깔아두었는지 확인
     * @param cardID 확인할 카드 ID
     * @return 확인결과
     */
    public boolean hasCardInField(Long cardID) {
        return cardField.contains(cardID);
    }

    /** TODO
     * place card in hand to field. card becomes available.
     * @param cardID card ID
     * @return result
     */
    public boolean placeCard(Long cardID){
        if(cardHand.remove(cardID)) {
            cardField.add(cardID);
            return true;
        }
        return false;
    }

    /**
     * 카드 획득은 card 에서 담당
     */
    public void addMajorCard(Long cardID){
        cardField.add(cardID);
    }

    /**
     * 모든 플레이어가 행동을 완했는지 확인
     * @return 만약 행동이 완료되었다면, true를 반환
     */
    @JsonIgnore
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
        this.playerBoard.breeding();
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

    /** TODO
     * 수확 때 1회 사용가능한 음식 교환을 모두 반환 (동물 제외)
     * @return (사용할 자원, 변환될 음식 양)인 튜플을 가지는 리스트
     */
    public List<ResourceStruct> resourceToFoodHarvest() {
        return cardField.stream()
                .map(game.getCardDictionary().getCardDict()::get)
                .map(Card::getResourceToFoodHarvest)
                .toList();
    }

    /** TODO
     * 카드 중 언제든 음식 교환이 있는 경우 가장 좋은 효율을 가진 경우들을 모아서 반환
     * @return (사용할 자원, 변환될 음식 양)인 튜플을 가지는 리스트
     */
    public List<ResourceStruct> resourceToFoodAnytime() {
        return cardField.stream()
                .map(game.getCardDictionary().cardDict::get)
                .flatMap(card -> Arrays.stream(card.getResourcesToFoodAnytime()))
                .collect(Collectors.groupingBy(ResourceStruct::getResource))
                .values().stream()
                .map(tuples -> tuples.stream()
                        .max(Comparator.comparingInt(ResourceStruct::getCount))
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 카드 중 언제든 음식 교환이 있는 경우 가장 좋은 효율을 가진 경우들을 모아서 반환
     * @return (사용할 동물, 변환될 음식 양)인 튜플을 가지는 리스트
     */
    public List<AnimalStruct> animalToFoodAnytime(){
        return cardField.stream()
                .map(game.getCardDictionary().cardDict::get)
                .flatMap(card -> Arrays.stream(card.getAnimalsToFoodAnytime()))
                .collect(Collectors.groupingBy(AnimalStruct::getAnimal))
                .values().stream()
                .map(tuples -> tuples.stream()
                        .max(Comparator.comparingInt(AnimalStruct::getCount))
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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
    @JsonIgnore
    public int getFamilyNumber() {
        return this.playerBoard.getFamilyCount();
    }

    /**
     * 액션을 플레이한다.
     */
    public void playAction() {
        this.playerBoard.playAction();
    }

    public int numField(FieldType fieldType) { return playerBoard.numField(fieldType); }

}
