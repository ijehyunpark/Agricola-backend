package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.resource.AnimalType;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MajorFactory {

    //화로1
    @Bean(name = "firePlace1")
    public FirePlace firePlace1() {
        return FirePlace.builder()
                .cardID(1L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(2).build()})
                .resourcesToFoodAnytime(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(2).build()})
                .animalsToFoodAnytime(new AnimalStruct[]{AnimalStruct.builder().animal(AnimalType.SHEEP).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.WILD_BOAR).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.CATTLE).count(3).build()})
                .bakeEfficiency(2)
                .build();
    }

    // 화로2
    @Bean(name = "firePlace2")
    public FirePlace firePlace2() {
        return FirePlace.builder()
                .cardID(2L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(3).build()})
                .resourcesToFoodAnytime(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(2).build()})
                .animalsToFoodAnytime(new AnimalStruct[]{AnimalStruct.builder().animal(AnimalType.SHEEP).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.WILD_BOAR).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.CATTLE).count(3).build()})
                .bakeEfficiency(2)
                .build();
    }

    //화덕
    @Bean(name = "firePlace3")
    public FirePlace firePlace3() {
        return FirePlace.builder()
                .cardID(3L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(4).build()})
                .resourcesToFoodAnytime(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(3).build()})
                .animalsToFoodAnytime(new AnimalStruct[]{AnimalStruct.builder().animal(AnimalType.SHEEP).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.WILD_BOAR).count(3).build(),
                        AnimalStruct.builder().animal(AnimalType.CATTLE).count(4).build()})
                .bakeEfficiency(3)
                .build();
    }

    //화덕
    @Bean(name = "firePlace4")
    public FirePlace firePlace4() {
        return FirePlace.builder()
                .cardID(4L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(5).build()})
                .resourcesToFoodAnytime(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(3).build()})
                .animalsToFoodAnytime(new AnimalStruct[]{AnimalStruct.builder().animal(AnimalType.SHEEP).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.WILD_BOAR).count(3).build(),
                        AnimalStruct.builder().animal(AnimalType.CATTLE).count(4).build()})
                .bakeEfficiency(3)
                .build();
    }

    //흙가마 (더미) // 빵효율 증가만 적용
    @Bean(name = "clayOven")
    public Oven clayOven(){
        return Oven.builder()
                .cardID(5L)
                .bonusPoint(2)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(3).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(1).build()})
                .bakeEfficiency(5)
                .build();
    }

    //돌가마 (더미) // 빵효율 증가만 적용
    @Bean(name = "stoneOven")
    public Oven stoneOven(){
        return Oven.builder()
                .cardID(6L)
                .bonusPoint(3)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(1).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(3).build()})
                .bakeEfficiency(4)
                .build();
    }

    //가구제작소
    @Bean(name = "woodWorkShop")
    public Workshop woodWorkShop(){
        return Workshop.builder()
                .cardID(7L)
                .bonusPoint(2)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(2).build()})
                .resourceToFoodHarvest(ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build())
                .resourceTypeToPoints(ResourceType.WOOD)
                .resourceNumToPoints(new int[][]{{3,1},{5,2},{7,3}})
                .build();
    }

    //그릇제작소
    @Bean(name = "clayWorkShop")
    public Workshop clayWorkShop(){
        return Workshop.builder()
                .cardID(8L)
                .bonusPoint(2)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(2).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(2).build()})
                .resourceToFoodHarvest(ResourceStruct.builder().resource(ResourceType.CLAY).count(2).build())
                .resourceTypeToPoints(ResourceType.CLAY)
                .resourceNumToPoints(new int[][]{{3,1},{5,2},{7,3}})
                .build();
    }

    //바구니제작소
    @Bean(name = "reedWorkShop")
    public Workshop reedWorkShop(){
        return Workshop.builder()
                .cardID(9L)
                .bonusPoint(2)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.REED).count(2).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(2).build()})
                .resourceToFoodHarvest(ResourceStruct.builder().resource(ResourceType.REED).count(2).build())
                .resourceTypeToPoints(ResourceType.REED)
                .resourceNumToPoints(new int[][]{{3,1},{5,2},{7,3}})
                .build();
    }

    //우물
    @Bean
    public Well well(){
        return Well.builder()
                .cardID(10L)
                .bonusPoint(4)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(1).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(3).build()})
                .rounds(new int[]{1,2,3,4,5})
                .stackResource(ResourceStruct.builder().resource(ResourceType.FOOD).count(1).build())
                .isStaticRound(false)
                .build();
    }
}
