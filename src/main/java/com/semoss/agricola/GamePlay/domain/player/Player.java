package com.semoss.agricola.GamePlay.domain.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.card.ActionTrigger;
import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorCard;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.ResourceBonusPointTrigger;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.FieldBonusPoint;
import com.semoss.agricola.GamePlay.domain.card.Occupation.FinishTrigger;
import com.semoss.agricola.GamePlay.domain.card.Occupation.HarvestTrigger;
import com.semoss.agricola.GamePlay.domain.resource.*;
import com.semoss.agricola.GamePlay.exception.BlockingException;
import com.semoss.agricola.GamePlay.exception.IllegalRequestException;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * 게임 플레이어
 */
@Getter
@NoArgsConstructor
public class Player {
    private Long userId;
    private boolean startingToken;
    private final EnumMap<ResourceType,Integer> resources = new EnumMap<>(ResourceType.class);
    private final PlayerBoard playerBoard = PlayerBoard.builder().build();
    private static final int ROUND_STACK_RESOURCE_SIZE = 15;
    private final List<List<ResourceStructInterface>> roundStackResource = new ArrayList<>(ROUND_STACK_RESOURCE_SIZE);


    @Builder
    public Player(Long userId, boolean isStartPlayer){
        this.userId = userId;
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
            if (round >= ROUND_STACK_RESOURCE_SIZE) throw new IllegalRequestException("잘못된 라운드가 입력되었습니다.");
            roundStackResource.get(round).add(resourceStruct);
        }
    }

    /**
     * round stack 에 자원을 쌓아둔다.
     * @param rounds 쌓을 라운드들
     * @param animalStruct 쌓을 동물
     */
    public void addRoundStack(int[] rounds, AnimalStruct animalStruct){
        for (int round : rounds) {
            if (round >= ROUND_STACK_RESOURCE_SIZE) throw new IllegalRequestException("잘못된 라운드가 입력되었습니다.");
            roundStackResource.get(round).add(animalStruct);
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
     * 동물을 소모한다.
     * @param animalType
     * @param num
     */
    public void useAnimal(AnimalType animalType, int num) {
        List<int[]> positions = playerBoard.animalPos(animalType);
        int total = positions.stream()
                .mapToInt(ints -> ints[2])
                .sum();
        if (total < num)
            throw new ResourceLackException("동물 개수가 부족합니다.");

        int ptr = 0;
        while(total > 0) {
            int consume = Math.max(positions.get(ptr)[2], num);
            playerBoard.removeAnimal(positions.get(ptr)[0], positions.get(ptr)[1], consume);
            num -= consume;
        }

    }

    /**
     * 해당 라운드가 시작될 때 쌓여 있는 자원 획득
     * @param round 현재 라운드
     */
    public void getRoundStack(int round){
        for (ResourceStructInterface resourceStruct : roundStackResource.get(round)){
            if(resourceStruct.isResource())
                this.addResource((ResourceStruct) resourceStruct);
            else if (resourceStruct.isAnimal())
                this.addAnimal((AnimalStruct) resourceStruct);
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
        this.startingToken = true;
    }

    /**
     * 시작 토큰을 제거한다.
     */
    public void disableStartingToken() {
        this.startingToken = false;
    }

    /**
     * 현재 필드 단계를 반환한다.
     * @return
     */
    @JsonIgnore
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
    public void harvest(CardDictionary cardDictionary) {
        // 수확 산출물 계산
        List<ResourceStruct> outputs = this.playerBoard.harvest();

        // 플레이어 자원에 추가
        addResource(outputs);

        cardDictionary.getCards(this).stream()
                .filter(card -> card.getCardType() == CardType.OCCUPATION)
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
            throw new BlockingException("음식 토큰 부족");
    }

    /**
     * 수확 때 1회 사용가능한 음식 교환을 모두 반환 (동물 제외)
     * @return (사용할 자원, 변환될 음식 양)인 튜플을 가지는 리스트
     */
//    public List<ResourceStruct> resourceToFoodHarvest() {
//        return cardField.stream()
//                .map(game.getCardDictionary().getCardDict()::get)
//                .filter(card -> card instanceof CookingHarvestTrigger)
//                .map(card -> ((CookingHarvestTrigger) card).getResourceToFoodHarvest())
//                .toList();
//    }

    /**
     * 카드 중 언제든 음식 교환이 있는 경우 가장 좋은 효율을 가진 경우들을 모아서 반환
     * @return (사용할 자원, 변환될 음식 양)인 튜플을 가지는 리스트
     */
//    public List<ResourceStructInterface> resourceToFoodAnytime() {
//        return cardField.stream()
//                .map(game.getCardDictionary().cardDict::get)
//                .filter(card -> card instanceof CookingAnytimeTrigger)
//                .flatMap(card -> ((CookingAnytimeTrigger) card).getResourcesToFoodAnytime().stream())
//                .filter(ResourceStructInterface::isResource)
//                .map(resourceStructInterface -> (ResourceStruct) resourceStructInterface)
//                .collect(Collectors.groupingBy(ResourceStruct::getResource))
//                .values().stream()
//                .map(tuples -> tuples.stream()
//                        .max(Comparator.comparingInt(ResourceStruct::getCount))
//                        .orElse(null))
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//    }

    /**
     * 카드 중 언제든 음식 교환이 있는 경우 가장 좋은 효율을 가진 경우들을 모아서 반환
     * @return (사용할 동물, 변환될 음식 양)인 튜플을 가지는 리스트
     */
//    public List<AnimalStruct> animalToFoodAnytime(){
//        return cardField.stream()
//                .map(game.getCardDictionary().cardDict::get)
//                .filter(card -> card instanceof CookingAnytimeTrigger)
//                .flatMap(card -> ((CookingAnytimeTrigger) card).getResourcesToFoodAnytime().stream())
//                .filter(ResourceStructInterface::isAnimal)
//                .map(resourceStructInterface -> (AnimalStruct) resourceStructInterface)
//                .collect(Collectors.groupingBy(AnimalStruct::getAnimal))
//                .values().stream()
//                .map(tuples -> tuples.stream()
//                        .max(Comparator.comparingInt(AnimalStruct::getCount))
//                        .orElse(null))
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//    }

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
    public void playAction(History history, CardDictionary cardDictionary) {
        this.playerBoard.playAction();
        // 액션 트리거 발동
        cardDictionary.getCards(this).stream()
                .filter(ActionTrigger.class::isInstance)
                .map(ActionTrigger.class::cast)
                .forEach(card -> card.actionTrigger(this, history));
    }

    public int getNumField(FieldType fieldType) { return playerBoard.getNumField(fieldType); }

    public int numEmptyField(){ return playerBoard.numEmptyField(); }

    public int numStableInBarn() { return playerBoard.numStableInBarn(); }

    //TODO 각 카드 종류별로 판별
    public int getCardPointsResource(CardDictionary cardDictionary) {
        int result = 0;
        result += cardDictionary.getCards(this).stream()
                .filter(card -> card.getCardType() == CardType.MAJOR)
                .map(card -> (MajorCard) card)
                .filter(card -> card instanceof ResourceBonusPointTrigger)
                .mapToInt(value -> ((ResourceBonusPointTrigger) value).checkPoint(this))
                .sum();
        result += cardDictionary.getCards(this).stream()
                .filter(card -> card.getCardType() == CardType.MAJOR)
                .map(card -> (MajorCard) card)
                .filter(card -> card instanceof FieldBonusPoint)
                .mapToInt(value -> ((FieldBonusPoint) value).checkPoint(this))
                .sum();
        return result;
    }

    public int getCardBonusPoints(CardDictionary cardDictionary) {
        return cardDictionary.getCards(this).stream()
                .mapToInt(Card::getBonusPoint)
                .sum();
    }

    /**
     * 종료 트리거 발동
     */
    public void finish(CardDictionary cardDictionary) {
        cardDictionary.getCards(this).stream()
                .filter(card -> card.getCardType() == CardType.OCCUPATION)
                .filter(occupation -> occupation instanceof FinishTrigger)
                .map(occupation -> (FinishTrigger) occupation)
                .forEach(occupation -> occupation.finishTrigger(this));

    }

    /**
     * 플레이어 밭에 씨앗을 심습니다.
     */
    public void cultivate(int y, int x, ResourceType resourceType) {
        this.playerBoard.cultivate(y, x, resourceType);
    }

    public boolean needRelocation() {
        return !this.playerBoard.isMovAnimalArrEmpty();
    }

    public void relocation(int y, int x, int newY, int newX, int count) {
        this.playerBoard.relocation(y, x, newY, newX, count);
    }

    public void relocation(AnimalType animalType, Integer newY, Integer newX, Integer count) {
        this.playerBoard.relocation(animalType, newY, newX, count);
    }
}
