package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.RoomType;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.AnimalType;
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
        Map<RoomType, List<ResourceStruct>> costs = new HashMap<>();
        costs.put(RoomType.CLAY, List.of(
                ResourceStruct.builder()
                        .resource(ResourceType.CLAY)
                        .count(1)
                        .build(),
                ResourceStruct.builder()
                        .resource(ResourceType.REED)
                        .count(1)
                        .build()
                )
        );
        costs.put(RoomType.STONE, List.of(
                        ResourceStruct.builder()
                                .resource(ResourceType.STONE)
                                .count(1)
                                .build(),
                        ResourceStruct.builder()
                                .resource(ResourceType.REED)
                                .count(1)
                                .build()
                )
        );
        return RoomUpgradeAction.builder()
                .costs(costs)
                .build();
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
        return BuildFenceAction.builder()
                .requirements(ResourceStruct.builder()
                        .resource(ResourceType.WOOD)
                        .count(1)
                        .build())
                .build();
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
     * 자원 시장 (갈대 1개, 돌 1개, 식량 토큰 1개) 액션
     * @return 자원 시장 액션
     */
    @Bean(name = "resourceMarketAction")
    public BasicAction resourceMarketAction() {
        return BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.REED)
                        .count(1)
                        .build())
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.STONE)
                        .count(1)
                        .build())
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.FOOD)
                        .count(2)
                        .build())
                .build();
    }

    /**
     * 누적 나무 1개 액션
     * @return 누적 나무 1개 액션
     */
    @Bean(name = "stackWood1Action")
    public StackResourceAction stackWood1Action() {
        return StackResourceAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.WOOD)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 누적 나무 2개 액션
     * @return 누적 나무 2개 액션
     */
    @Bean(name = "stackWood2Action")
    public StackResourceAction stackWood2Action() {
        return StackResourceAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.WOOD)
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
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.WOOD)
                        .count(3)
                        .build())
                .build();
    }

    /**
     * 누적 흙 1개 액션
     * @return 누적 흙 1개 액션
     */
    @Bean(name = "stackClay1Action")
    public StackResourceAction stackClay1Action() {
        return StackResourceAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.CLAY)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 누적 갈대 1개 액션
     * @return 누적 갈대 1개 액션
     */
    @Bean(name = "stackReed1Action")
    public StackResourceAction stackReed1Action() {
        return StackResourceAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.REED)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 낚시 (누적 음식 1개) 액션
     * @return 낚시 액션
     */
    @Bean(name = "fishingAction")
    public StackResourceAction fishingAction() {
        return StackResourceAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.FOOD)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 유량극단 (누적 음식 1개) 액션
     * @return 유량극단 액션
     */
    @Bean(name = "flowExtremesAction")
    public StackResourceAction flowExtremesAction() {
        return StackResourceAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.FOOD)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 점토 채굴장 (누적 음식 1개) 액션
     * @return 점토 채굴장 액션
     */
    @Bean(name = "clayPitAction")
    public StackResourceAction clayPitAction() {
        return StackResourceAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.CLAY)
                        .count(2)
                        .build())
                .build();
    }

    /**
     * 양 시장 (누적 양 1개) 액션
     * @return  양 시장 액션
     */
    @Bean(name = "sheepMarketAction")
    public StackAnimalAction sheepMarketAction() {
        return StackAnimalAction.builder()
                .animal(AnimalStruct.builder()
                        .animal(AnimalType.SHEEP)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 돼지 시장 (누적 돼지 1개) 액션
     * @return  돼지 시장 액션
     */
    @Bean(name = "wildBoarMarketAction")
    public StackAnimalAction wildBoarMarketAction() {
        return StackAnimalAction.builder()
                .animal(AnimalStruct.builder()
                        .animal(AnimalType.WILD_BOAR)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 소 시장 (누적 소 1개) 액션
     * @return  소 시장 액션
     */
    @Bean(name = "cattleMarketAction")
    public StackAnimalAction cattleMarketAction() {
        return StackAnimalAction.builder()
                .animal(AnimalStruct.builder()
                        .animal(AnimalType.CATTLE)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 서부 채석장 (누적 돌 1개) 액션
     * @return 서부 채석장  액션
     */
    @Bean(name = "westernQuarryAction")
    public StackResourceAction westernQuarryAction() {
        return StackResourceAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.STONE)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 동부 채석장 (누적 돌 1개) 액션
     * @return 동부 채석장 액션
     */
    @Bean(name = "easternQuarryAction")
    public StackResourceAction easternQuarryAction() {
        return StackResourceAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.STONE)
                        .count(1)
                        .build())
                .build();
    }

    /**
     * 급하지 않은 가족 늘리기 액션
     * @return 급하지 않은 가족 늘리기 액션
     */
    @Bean("growFamilyWithoutUrgencyAction")
    public IncreaseFamilyAction growFamilyWithoutUrgencyAction() {
        return IncreaseFamilyAction.builder()
                .precondition(true)
                .build();
    }

    /**
     * 급한 가족 늘리기 액션
     * @return 급한 가족 늘리기 액션
     */
    @Bean("growFamilyWithUrgencyAction")
    public IncreaseFamilyAction growFamilyWithUrgencyAction() {
        return IncreaseFamilyAction.builder()
                .precondition(false)
                .build();
    }
}
