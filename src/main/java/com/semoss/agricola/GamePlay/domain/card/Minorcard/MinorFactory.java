package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.action.implement.ActionName;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class MinorFactory {

    @Bean(name = "windmill")
    public Windmill windmill(){
        return Windmill.builder()
                .cardID(17L)
                .bonusPoint(2)
                .ingredients(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.WOOD).count(3).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(1).build()
                })
                .preconditionCardType(null)
                .minCardNum(0)
                .resourcesToFoodAnytime(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.GRAIN).count(2).build()
                })
                .animalsToFoodAnytime(null)
                .name("풍차")
                .description("언제든 곡물을 음식 2개로 교환 가능")
                .build();
    }

    @Bean(name = "simpleFirePlace")
    public MinorFirePlace simpleFirePlace(){
        return MinorFirePlace.builder()
                .cardID(20L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.CLAY).count(1).build()
                })
                .preconditionCardType(null)
                .minCardNum(0)
                .resourcesToFoodAnytime(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(2).build()
                })
                .animalsToFoodAnytime(new AnimalStruct[]{
                        AnimalStruct.builder().animal(AnimalType.SHEEP).count(1).build(),
                        AnimalStruct.builder().animal(AnimalType.WILD_BOAR).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.CATTLE).count(3).build()
                })
                .bakeEfficiency(2)
                .name("간이화로")
                .description("언제든 자원을 음식으로 교환가능. 교환 비율 야채 : 2, 양 : 1, 돼지 : 2, 소 : 3, 빵굽기 효율 : 2")
                .build();
    }

    @Bean(name = "manager")
    public FieldBonusMinorCard manager(){
        return FieldBonusMinorCard.builder()
                .cardID(23L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build(),
                })
                .preconditionCardType(null)
                .minCardNum(0)
                .bonusFieldType(FieldType.BARN)
                .fieldBonusPoint(new int[]{0,0,0,0,0,0,1,2,3,4})
                .name("관리소")
                .description("게임 종료시 목초지 6/7/8/9+개가 있으면 1/2/3/4의 추가 점수 획득")
                .build();
    }

    @Bean(name = "animalPen")
    public StackResourceMinorCard animalPen(){
        return StackResourceMinorCard.builder()
                .cardID(24L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(4)
                .stackResource(ResourceStruct.builder().resource(ResourceType.FOOD).count(2).build())
                .rounds(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14})
                .isStaticRound(true)
                .name("동물 우리")
                .description("남은 라운드에 음식을 2개씩 두고 해당 라운드가 시작될 때 쌓아둔 음식을 가져감")
                .build();
    }

    @Bean(name = "canoe")
    public ActionMinorCard canoe(){
        return ActionMinorCard.builder()
                .cardID(30L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build()})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(2)
                .actionName(ActionName.ACTION10)
                .bonusResources(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.FOOD).count(1).build(),
                        ResourceStruct.builder().resource(ResourceType.REED).count(1).build()
                })
                .name("카누")
                .description("“낚시”액션 공간을 사용할 때 마다 추가 음식 1개와 갈대 1개를 획득")
                .build();
    }

    @Bean(name = "cornScoop")
    public ActionMinorCard cornScoop(){
        return ActionMinorCard.builder()
                .cardID(35L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(1).build()})
                .preconditionCardType(null)
                .minCardNum(0)
                .actionName(ActionName.ACTION3)
                .bonusResources(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.GRAIN).count(1).build()
                })
                .name("옥수수 국자")
                .description("“곡물 1” 액션을 사용할 때마다 추가 곡물 1개를 획득")
                .build();
    }

    @Bean(name = "marketStall")
    public MoveLeftMinorCard marketStall(){
        return MoveLeftMinorCard.builder()
                .cardID(39L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.GRAIN).count(1).build()})
                .preconditionCardType(null)
                .minCardNum(0)
                .bonusResource(ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(1).build())
                .name("시장 마구간")
                .description("야채 1개 획득")
                .build();
    }

    @Bean(name = "fruitTree")
    public StackResourceMinorCard fruitTree(){
        return StackResourceMinorCard.builder()
                .cardID(43L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(3)
                .stackResource(ResourceStruct.builder().resource(ResourceType.FOOD).count(1).build())
                .rounds(new int[]{8,9,10,11,12,13,14})
                .isStaticRound(true)
                .name("과일나무")
                .description("나머지 8~14라운드에 음식을 1개씩 두고 해당 라운드가 시작될 때 쌓아둔 음식을 가져감")
                .build();
    }

    @Bean(name = "sackCart")
    public StackResourceMinorCard sackCart(){
        return StackResourceMinorCard.builder()
                .cardID(46L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build()})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(2)
                .stackResource(ResourceStruct.builder().resource(ResourceType.GRAIN).count(1).build())
                .rounds(new int[]{5,8,11,14})
                .isStaticRound(true)
                .name("자루 카트")
                .description("5,8,11,14라운드에 곡물을 1개씩 두고 해당 라운드가 시작될 때 쌓아둔 음식을 가져감")
                .build();
    }

    @Bean(name = "reedPond")
    public StackResourceMinorCard reedPond(){
        return StackResourceMinorCard.builder()
                .cardID(48L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(3)
                .stackResource(ResourceStruct.builder().resource(ResourceType.REED).count(1).build())
                .rounds(new int[]{1,2,3})
                .isStaticRound(false)
                .name("갈대연못")
                .description("다음 3개의 라운드에 갈대를 1개씩 두고 해당 라운드가 시작될 때 쌓아둔 갈대를 가져감")
                .build();
    }

    @Bean(name = "grainCart")
    public ActionMinorCard grainCart(){
        return ActionMinorCard.builder()
                .cardID(74L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build()})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(2)
                .actionName(ActionName.ACTION3)
                .bonusResources(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.GRAIN).count(2).build()
                })
                .name("곡물 카트")
                .description("“곡물 1”액션 공간을 사용할 때 마다 2개의 추가 곡물을 획득")
                .build();
    }

    @Bean(name = "weeklyMarket")
    public MoveLeftMinorCard weeklyMarket(){
        return MoveLeftMinorCard.builder()
                .cardID(104L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.GRAIN).count(3).build()
                })
                .preconditionCardType(null)
                .minCardNum(0)
                .bonusResource(ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(2).build())
                .name("주간 시장")
                .description("야채 2개 획득")
                .build();
    }

    @Bean(name = "brewery")
    public CookingHarvestMinorCard brewery(){
        return CookingHarvestMinorCard.builder()
                .cardID(110L)
                .bonusPoint(2)
                .ingredients(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.GRAIN).count(2).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(2).build()
                })
                .preconditionCardType(null)
                .minCardNum(0)
                .resourceToFoodHarvest(ResourceStruct.builder().resource(ResourceType.GRAIN).count(3).build())
                .name("양조장")
                .description("수확 시기에 곡물 1개를 3개의 음식으로 교환 가능")
                .build();
    }

    // 더미카드입니다. 구걸카드 1장 획득
    @Bean(name = "dummyMinorCard")
    public DummyMinorCard dummyMinorCard(){
        return DummyMinorCard.builder()
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{})
                .preconditionCardType(null)
                .minCardNum(0)
                .name("더미 보조설비")
                .description("구걸토큰 1개를 획득합니다.")
                .build();
    }
}
