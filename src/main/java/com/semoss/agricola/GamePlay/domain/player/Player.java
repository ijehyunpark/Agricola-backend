package com.semoss.agricola.GamePlay.domain.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.CookingAnytimeTrigger;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.CookingHarvestTrigger;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.ResourceBonusPointTrigger;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.FieldBonusPoint;
import com.semoss.agricola.GamePlay.domain.card.Occupation.ActionTrigger;
import com.semoss.agricola.GamePlay.domain.card.Occupation.FinishTrigger;
import com.semoss.agricola.GamePlay.domain.card.Occupation.HarvestTrigger;
import com.semoss.agricola.GamePlay.domain.card.Occupation.Occupation;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.IllegalRequestException;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import com.semoss.agricola.GamePlay.exception.ServerError;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private final List<Occupation> occupations = new ArrayList<>();
    private List<ResourceStruct>[] roundStackResource = new ArrayList[15];

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
     * @return 만약 플레이어가 빈 방을 가지고 있다면 true, 아닌 경우 false 반환
     */
    public boolean existEmptyRoom(){
        return playerBoard.existEmptyRoom();
    }

    /**
     * 플레이어의 자원 수 확인
     * @param resourceType 확인할 자원 종류
     * @return 자원 수
     */
    public int getResource(ResourceType resourceType){
        return resources.get(resourceType);
    }

    public int getAnimal(AnimalType animalType) {
        return playerBoard.getAnimal(animalType);
    }

    /**
     * 저장소에 자원 저장
     * @param resource 저장할 자원
     */
    public void addResource(ResourceStruct resource){
        resources.compute(resource.getResource(), (key, value) -> value + resource.getCount());
    }
    /**
     * 저장소에 자원 저장 (위의 메소드로 대체 됐음)
     * @param resourceType 자원 종류
     * @param num 자원 수
     */
    public void addResource(ResourceType resourceType, int num){
        resources.compute(resourceType, (key, value) -> value + num);
    }

    /**
     * 저장소에 자원 저장
     * @param resources 저장할 자원 리스트
     */
    public void addResource(List<ResourceStruct> resources) {
        for (ResourceStruct resource : resources) {
            addResource(resource.getResource(), resource.getCount());
        }
    }

    /**
     * round stack 에 자원을 쌓아둔다.
     * @param rounds 쌓을 라운드들
     * @param resourceStruct 쌓을 자원
     */
    public void addRoundStack(int[] rounds, ResourceStruct resourceStruct){
        for (int round : rounds) {
            if (round >= roundStackResource.length) throw new IllegalRequestException("잘못된 라운드가 입력되었습니다.");
            roundStackResource[round].add(resourceStruct);
        }
    }

    /**
     * 가축을 추가한다.
     * @param animalType 추가할 가축 종류
     * @param count 추가할 가축 개수
     */
    public void addAnimal(AnimalType animalType, int count) {
        this.playerBoard.addAnimal(animalType, count);
    }


    /**
     * 가축을 추가한다.
     * @param animal 추가할 가축 구조
     */
    public void addAnimal(AnimalStruct animal) {
        addAnimal(animal.getAnimal(), animal.getCount());
    }

    /**
     * 가축을 추가한다.
     * @param animals 추가할 가축 구조 리스트
     */
    public void addAnimal(List<AnimalStruct> animals) {
        for (AnimalStruct animal : animals) {
            addAnimal(animal);
        }
    }

    /**
     * 자원 사용
     * @param resourceType 사용할 자원
     * @param num 사용할 자원 수
     */
    public void useResource(ResourceType resourceType, int num){
        if (resources.get(resourceType) < num)
            throw new ResourceLackException("자원이 부족합니다.");
        resources.compute(resourceType, (key, value) -> value - num);
    }

    /**
     * 자원 사용
     * @param resource 사용할 자원
     */
    public void useResource(ResourceStruct resource){
        useResource(resource.getResource(), resource.getCount());
    }

    /**
     * 자원 사용
     * @param resources 사용할 자원 리스트
     */
    public void useResource(List<ResourceStruct> resources){
        for (ResourceStruct resource : resources) {
            useResource(resource);
        }
    }

    /**
     * 해당 라운드가 시작될 때 쌓여 있는 자원 획득
     * @param round 현재 라운드
     */
    public void getRoundStack(int round){
        for (ResourceStruct resourceStruct : roundStackResource[round]){
            this.addResource(resourceStruct);
        }
    }

    /**
     * 자원 확인
     * @param resourceType 확인할 자원
     * @return ResourceStruct 형태로 반환
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
                .filter(Player::isStartingToken)
                .findAny()
                .orElseThrow(ServerError::new)
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
        return playerBoard.getNumField(FieldType.ROOM);
    }

    /**
     * 건축물 건설
     *
     * @param y            건설할 가로축 좌표
     * @param x            건설할 세로축 좌표
     * @param fieldType    건설할 건물 종류
     * @return
     */
    public void buildField(int y, int x, FieldType fieldType){
        this.playerBoard.buildField(y, x, fieldType);
    }

    /**
     * 울타리를 건설한다.
     * @param rowPosition 건설할 울타리 row 좌표
     * @param colPosition 건설할 울타리 col 좌표
     */
    public void buildFence(int[][] rowPosition,  int[][] colPosition) {
        this.playerBoard.buildFence(rowPosition, colPosition);
    }

    public void addStable(int row, int col){
        playerBoard.addStable(row,col);
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

    /**
     * 카드 타입의 카드를 필드에 얼마나 깔았는지 확인
     * @param cardType 확인할 카드 타입
     * @return 필드에 깐 카드 개수
     */
    public int numCardInField(CardType cardType) {
        CardDictionary cardDictionary = game.getCardDictionary();
        return (int) cardField.stream()
                .filter(id -> cardDictionary.getCard(id).getCardType() == cardType)
                .count();
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
        game.getCardDictionary().changeOwner(cardID,this);
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


        this.occupations.stream()
                .filter(occupation -> occupation instanceof HarvestTrigger)
                .map(occupation -> (HarvestTrigger) occupation)
                .forEach(occupation -> occupation.harvestTrigger(this));
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
            throw new ResourceLackException("음식 토큰 부족");
    }

    /** TODO
     * 수확 때 1회 사용가능한 음식 교환을 모두 반환 (동물 제외)
     * @return (사용할 자원, 변환될 음식 양)인 튜플을 가지는 리스트
     */
    public List<ResourceStruct> resourceToFoodHarvest() {
        return cardField.stream()
                .map(game.getCardDictionary().getCardDict()::get)
                .filter(card -> card instanceof CookingHarvestTrigger)
                .map(card -> ((CookingHarvestTrigger) card).getResourceToFoodHarvest())
                .toList();
    }

    /** TODO
     * 카드 중 언제든 음식 교환이 있는 경우 가장 좋은 효율을 가진 경우들을 모아서 반환
     * @return (사용할 자원, 변환될 음식 양)인 튜플을 가지는 리스트
     */
    public List<ResourceStruct> resourceToFoodAnytime() {
        return cardField.stream()
                .map(game.getCardDictionary().cardDict::get)
                .filter(card -> card instanceof CookingAnytimeTrigger)
                .flatMap(card -> Arrays.stream(((CookingAnytimeTrigger) card).getResourcesToFoodAnytime()))
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
                .filter(card -> card instanceof CookingAnytimeTrigger)
                .flatMap(card -> Arrays.stream(((CookingAnytimeTrigger) card).getAnimalsToFoodAnytime()))
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
    public void playAction(History history) {
        this.playerBoard.playAction();
        // 액션 트리거 발동
        this.occupations.stream()
                .filter(occupation -> occupation instanceof ActionTrigger)
                .map(occupation -> (ActionTrigger) occupation)
                .forEach(occupation -> occupation.actionTrigger(this, history));
    }

    public int getNumField(FieldType fieldType) { return playerBoard.getNumField(fieldType); }

    public int numEmptyField(){ return playerBoard.numEmptyField(); }

    public int numStableInBarn() { return playerBoard.numStableInBarn(); }

    //TODO 각 카드 종류별로 판별
    public int getCardPointsResource(CardDictionary cardDictionary) {
        int result = 0;
        result += (int)cardField.stream()
                .filter(id -> cardDictionary.getCard(id) instanceof ResourceBonusPointTrigger)
                .mapToLong(id -> ((ResourceBonusPointTrigger)cardDictionary.getCard(id)).checkPoint(this))
                .sum();
        result += (int)cardField.stream()
                .filter(id -> cardDictionary.getCard(id) instanceof FieldBonusPoint)
                .mapToLong(id -> ((FieldBonusPoint)cardDictionary.getCard(id)).checkPoint(this))
                .sum();
        return result;
    }

    public int getCardBonusPoints(CardDictionary cardDictionary) {
        return (int)cardField.stream()
                .mapToLong(id -> cardDictionary.getCard(id).getBonusPoint())
                .sum();
    }


    /**
     * 직업 추가
     * @param occupation
     */
    public void addOccupations(Occupation occupation) {
        this.occupations.add(occupation);
    }

    /**
     * 종료 트리거 발동
     */
    public void finish() {
        this.occupations.stream()
                .filter(occupation -> occupation instanceof ActionTrigger)
                .map(occupation -> (FinishTrigger) occupation)
                .forEach(occupation -> occupation.finishTrigger(this));

    }

    /**
     * 플레이어 밭에 씨앗을 심습니다.
     */
    public void cultivate(int y, int x, ResourceType resourceType) {
        this.playerBoard.cultivate(y, x, resourceType);
    }

}
