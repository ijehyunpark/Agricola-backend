package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.action.implement.ActionName;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.resource.AnimalType;
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

    @Bean
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
                .build();
    }

    @Bean
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
                .build();
    }

    @Bean
    public FieldBonusMinorCard Manager(){
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
                .build();
    }

    @Bean
    public StackResourceMinorCard AnimalPen(){
        return StackResourceMinorCard.builder()
                .cardID(24L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(4)
                .stackResource(ResourceStruct.builder().resource(ResourceType.FOOD).count(2).build())
                .rounds(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14})
                .isStaticRound(true)
                .build();
    }

    @Bean
    public ActionNameMinorCard Canoe(){
        return ActionNameMinorCard.builder()
                .cardID(30L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build()})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(2)
                .actionNames(new ActionName[]{ActionName.ACTION10})
                .bonusResources(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.FOOD).count(1).build(),
                        ResourceStruct.builder().resource(ResourceType.REED).count(1).build()
                })
                .build();
    }

    @Bean
    public ActionNameMinorCard CornScoop(){
        return ActionNameMinorCard.builder()
                .cardID(35L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(1).build()})
                .preconditionCardType(null)
                .minCardNum(0)
                .actionNames(new ActionName[]{ActionName.ACTION3})
                .bonusResources(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.GRAIN).count(1).build()
                })
                .build();
    }

    @Bean
    public MoveLeftMinorCard MarketStall(){
        return MoveLeftMinorCard.builder()
                .cardID(39L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.GRAIN).count(1).build()})
                .preconditionCardType(null)
                .minCardNum(0)
                .bonusResource(ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(1).build())
                .build();
    }

    @Bean
    public StackResourceMinorCard FruitTree(){
        return StackResourceMinorCard.builder()
                .cardID(43L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(3)
                .stackResource(ResourceStruct.builder().resource(ResourceType.FOOD).count(1).build())
                .rounds(new int[]{8,9,10,11,12,13,14})
                .isStaticRound(true)
                .build();
    }

    @Bean
    public StackResourceMinorCard SackCart(){
        return StackResourceMinorCard.builder()
                .cardID(46L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build()})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(2)
                .stackResource(ResourceStruct.builder().resource(ResourceType.GRAIN).count(1).build())
                .rounds(new int[]{5,8,11,14})
                .isStaticRound(true)
                .build();
    }

    @Bean
    public StackResourceMinorCard ReedPond(){
        return StackResourceMinorCard.builder()
                .cardID(48L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(3)
                .stackResource(ResourceStruct.builder().resource(ResourceType.REED).count(1).build())
                .rounds(new int[]{1,2,3})
                .isStaticRound(false)
                .build();
    }

    @Bean
    public ActionNameMinorCard GrainCart(){
        return ActionNameMinorCard.builder()
                .cardID(74L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build()})
                .preconditionCardType(CardType.OCCUPATION)
                .minCardNum(2)
                .actionNames(new ActionName[]{ActionName.ACTION3})
                .bonusResources(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.GRAIN).count(2).build()
                })
                .build();
    }

    @Bean
    public MoveLeftMinorCard WeeklyMarket(){
        return MoveLeftMinorCard.builder()
                .cardID(104L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{
                        ResourceStruct.builder().resource(ResourceType.GRAIN).count(3).build()
                })
                .preconditionCardType(null)
                .minCardNum(0)
                .bonusResource(ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(2).build())
                .build();
    }

    @Bean
    public CookingHarvestMinorCard Brewery(){
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
                .build();
    }

    // 더미카드입니다. 구걸카드 1장 획득
    @Bean
    public MoveLeftMinorCard DummyMinorCard(){
        return MoveLeftMinorCard.builder()
                .cardID(999L)
                .bonusPoint(0)
                .ingredients(new ResourceStruct[]{})
                .preconditionCardType(null)
                .minCardNum(0)
                .bonusResource(ResourceStruct.builder().resource(ResourceType.BEGGING).count(1).build())
                .build();
    }
}
