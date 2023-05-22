package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.RoomType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ActionFactory {

    private BuildSimpleAction buildRoomSimpleAction(ResourceType resourceType) {
        return BuildSimpleAction.builder()
                .fieldType(FieldType.ROOM)
                .requirements(List.of(
                        ResourceStruct.builder()
                                .resource(resourceType)
                                .count(5)
                                .build(),
                        ResourceStruct.builder()
                                .resource(ResourceType.REED)
                                .count(2)
                                .build()
                ))
                .buildMaxCount(-1)
                .build();
    }

    /**
     * 집 한 번 고치기 액션
     * @return 집 한 번 고치기 액션
     */
    @Bean
    public RoomUpgradeAction roomUpgradeAction() {
        return RoomUpgradeAction.builder().build();
    }

    /**
     * 집 건설 액션
     * @return 집 건설 액션
     */
    @Bean
    public BuildRoomAction buildRoomAction() {
        Map<RoomType, BuildSimpleAction> buildActions = new HashMap<>();
        buildActions.put(RoomType.WOOD, buildRoomSimpleAction(ResourceType.WOOD));
        buildActions.put(RoomType.CLAY, buildRoomSimpleAction(ResourceType.CLAY));
        buildActions.put(RoomType.STONE, buildRoomSimpleAction(ResourceType.STONE));

        return BuildRoomAction.builder()
                .buildActions(buildActions)
                .build();
    }

    /**
     * 외양간 건설 액션
     * @return 외양간 건설 액션
     */
    @Bean(name = "buildStableAction")
    public BuildSimpleAction buildStableAction() {
        return BuildSimpleAction.builder()
                .fieldType(FieldType.STABLE)
                .requirements(List.of(ResourceStruct.builder()
                        .resource(ResourceType.WOOD)
                        .count(2)
                        .build()))
                .buildMaxCount(-1)
                .build();
    }

    /**
     * 울타리 건설 액션
     * @return 울타리 건설 액션
     */
    @Bean
    public BuildFenceAction buildFenceAction() {
        return BuildFenceAction.builder().build();
    }


    /**
     * 밭 일구기 액션
     * @return 밭 일구기 액션
     */
    @Bean(name = "buildFarmAction")
    public BuildSimpleAction buildFarmAction() {
        return BuildSimpleAction.builder()
                .fieldType(FieldType.FARM)
                .requirements(new ArrayList<>())
                .buildMaxCount(1)
                .build();
    }

    /**
     * 시작 플레이어 되기 액션
     * @return 시작 플레이어 되기 액션
     */
    @Bean
    public GetStartingPositionAction getStartingPositionAction(){
        return GetStartingPositionAction.builder().build();
    }

    /**
     * 주요 카드 내려놓기 액션
     * @return 주요 카드 내려놓기 액션
     */
    @Bean(name = "placeMajorCardAction")
    public PlaceAction placeMajorCardAction(){
        return PlaceAction.builder()
                .cardType(CardType.MAJOR)
                .build();
    }

    /**'
     * 보조 설비 내려놓기 액션
     * @return 보조 설비 내려놓기 액션
     */
    @Bean(name = "placeMinorCardAction")
    public PlaceAction placeMinorCardAction(){
        return PlaceAction.builder()
                .cardType(CardType.MINOR)
                .build();
    }

    /**
     * 직업 카드 내려놓기 액션
     * @return 직업 카드 내려놓기 액션
     */
    @Bean(name = "placeOccupationCardAction")
    public PlaceAction placeOccupationCardAction(){
        return PlaceAction.builder()
                .cardType(CardType.OCCUPATION)
                .build();
    }

    /**
     * 씨 뿌리기 액션
     * @return 씨 뿌리기 액션
     */
    @Bean
    public CultivationAction cultivationAction() {
        return CultivationAction.builder().build();
    }

    /**
     * 빵 굽기 액션
     * @return 빵 굽기 액션
     */
    @Bean
    public BakeAction bakeAction() {
        return BakeAction.builder().build();
    }

    /**
     * 곡식 1개 가져가기 액션
     * @return 곡식 1개 가져가기 액션
     */
    @Bean(name = "getGrain1Action")
    public BasicAction getGrain1Action(){
        return BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.GRAIN)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 채소 종자 (채소 1개 가져가기) 액션
     * @return 채소 종자 액션
     */
    @Bean(name = "getVegetable1Action")
    public BasicAction getVegetable1Action(){
        return BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.VEGETABLE)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 날품팔이 (식량 2개 가져가기) 액션
     * @return 날품팔이 액션
     */
    @Bean(name = "datallerAction")
    public BasicAction datallerAction(){
        return BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.FOOD)
                        .count(2)
                        .build())
                .build();
    }

    /**
     * 누적 나무 3개 액션
     * @return 누적 나무 3개 액션
     */
    @Bean(name = "stackWood3Action")
    public StackResourceAction stackWood3Action() {
        return StackResourceAction.builder()
                .resourceType(ResourceType.WOOD)
                .stackCount(3)
                .build();
    }

    /**
     * 누적 흙 1개 액션
     * @return 누적 흙 1개 액션
     */
    @Bean(name = "stackClay1Action")
    public StackResourceAction stackClay1Action() {
        return StackResourceAction.builder()
                .resourceType(ResourceType.CLAY)
                .stackCount(1)
                .build();
    }

    /**
     * 누적 갈대 1개 액션
     * @return 누적 갈대 1개 액션
     */
    @Bean(name = "stackReed1Action")
    public StackResourceAction stackReed1Action() {
        return StackResourceAction.builder()
                .resourceType(ResourceType.REED)
                .stackCount(1)
                .build();
    }

    /**
     * 낚시 (누적 음식 1개) 액션
     * @return 낚시 액션
     */
    @Bean(name = "fishingAction")
    public StackResourceAction fishingAction() {
        return StackResourceAction.builder()
                .resourceType(ResourceType.REED)
                .stackCount(1)
                .build();
    }

    /**
     * 양 시장 (누적 양 1개) 액션
     * @return  양 시장 액션
     */
    @Bean(name = "sheepMarketAction")
    public StackAnimalAction sheepMarketAction() {
        return StackAnimalAction.builder()
                .animalType(AnimalType.SHEEP)
                .stackCount(1)
                .build();
    }

    /**
     * 돼지 시장 (누적 돼지 1개) 액션
     * @return  돼지 시장 액션
     */
    @Bean(name = "wildBoarMarketAction")
    public StackAnimalAction wildBoarMarketAction() {
        return StackAnimalAction.builder()
                .animalType(AnimalType.WILD_BOAR)
                .stackCount(1)
                .build();
    }

    /**
     * 소 시장 (누적 소 1개) 액션
     * @return  소 시장 액션
     */
    @Bean(name = "cattleMarketAction")
    public StackAnimalAction cattleMarketAction() {
        return StackAnimalAction.builder()
                .animalType(AnimalType.CATTLE)
                .stackCount(1)
                .build();
    }

    /**
     * 서부 채석장 (누적 돌 1개) 액션
     * @return 서부 채석장  액션
     */
    @Bean(name = "westernQuarryAction")
    public StackResourceAction westernQuarryAction() {
        return StackResourceAction.builder()
                .resourceType(ResourceType.STONE)
                .stackCount(1)
                .build();
    }

    /**
     * 동부 채석장 (누적 돌 1개) 액션
     * @return 동부 채석장 액션
     */
    @Bean(name = "easternQuarryAction")
    public StackResourceAction easternQuarryAction() {
        return StackResourceAction.builder()
                .resourceType(ResourceType.STONE)
                .stackCount(1)
                .build();
    }

    /**
     * 급하지 않은 가족 늘리기 액션
     * @return 급하지 않은 가족 늘리기 액션
     */
    @Bean("growFamilyWithoutUrgencyAction")
    public IncreaseFamily growFamilyWithoutUrgencyAction() {
        return IncreaseFamily.builder()
                .precondition(true)
                .build();
    }

    /**
     * 급한 가족 늘리기 액션
     * @return 급한 가족 늘리기 액션
     */
    @Bean("growFamilyWithUrgencyAction")
    public IncreaseFamily growFamilyWithUrgencyAction() {
        return IncreaseFamily.builder()
                .precondition(false)
                .build();
    }
}
