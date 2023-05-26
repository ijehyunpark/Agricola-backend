package com.semoss.agricola.GamePlay.domain.gameboard;

import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.MajorCard;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ImprovementBoard {
    private final List<Long> majorCards;

    public ImprovementBoard(CardDictionary cardDictionary){
        majorCards = new ArrayList<>();
        //화로
        cardDictionary.addCard(1L, MajorCard.builder()
                .cardID(1L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(2).build()})
                .resourcesToFoodAnytime(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(2).build()})
                .animalsToFoodAnytime(new AnimalStruct[]{AnimalStruct.builder().animal(AnimalType.SHEEP).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.WILD_BOAR).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.CATTLE).count(3).build()})
                .bakeEfficiency(2).build());
        majorCards.add(1L);

        //화로
        cardDictionary.addCard(2L, MajorCard.builder()
                .cardID(2L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(3).build()})
                .resourcesToFoodAnytime(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(2).build()})
                .animalsToFoodAnytime(new AnimalStruct[]{AnimalStruct.builder().animal(AnimalType.SHEEP).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.WILD_BOAR).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.CATTLE).count(3).build()})
                .bakeEfficiency(2).build());
        majorCards.add(2L);

        //화덕 (카드 반납은 구현 안돼있음)
        cardDictionary.addCard(3L, MajorCard.builder()
                .cardID(3L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(4).build()})
                .resourcesToFoodAnytime(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(3).build()})
                .animalsToFoodAnytime(new AnimalStruct[]{AnimalStruct.builder().animal(AnimalType.SHEEP).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.WILD_BOAR).count(3).build(),
                        AnimalStruct.builder().animal(AnimalType.CATTLE).count(4).build()})
                .bakeEfficiency(3).build());
        majorCards.add(3L);

        //화덕 (카드 반납은 구현 안돼있음)
        cardDictionary.addCard(4L, MajorCard.builder()
                .cardID(4L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(5).build()})
                .resourcesToFoodAnytime(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(3).build()})
                .animalsToFoodAnytime(new AnimalStruct[]{AnimalStruct.builder().animal(AnimalType.SHEEP).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.WILD_BOAR).count(3).build(),
                        AnimalStruct.builder().animal(AnimalType.CATTLE).count(4).build()})
                .bakeEfficiency(3).build());
        majorCards.add(4L);

        //흙가마 (더미) // 빵효율 증가만 적용
        cardDictionary.addCard(5L, MajorCard.builder()
                .cardID(5L)
                .bonusPoint(2)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(3).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(1).build()})
                .bakeEfficiency(5).build());
        majorCards.add(5L);

        //돌가마 (더미) // 빵효율 증가만 적용
        cardDictionary.addCard(6L, MajorCard.builder()
                .cardID(6L)
                .bonusPoint(3)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(1).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(3).build()})
                .bakeEfficiency(4).build());
        majorCards.add(6L);

        //가구제작소
        cardDictionary.addCard(7L, MajorCard.builder()
                .cardID(7L)
                .bonusPoint(2)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(2).build()})
                .resourceToFoodHarvest(ResourceStruct.builder().resource(ResourceType.CLAY).count(2).build())
                .resourceTypeToPoints(ResourceType.CLAY)
                .resourceNumToPoints(new int[][]{{3,1},{5,2},{7,3}}).build());
        majorCards.add(7L);

        //그릇제작소
        cardDictionary.addCard(8L, MajorCard.builder()
                .cardID(8L)
                .bonusPoint(2)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.REED).count(2).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(2).build()})
                .resourceToFoodHarvest(ResourceStruct.builder().resource(ResourceType.REED).count(2).build())
                .resourceTypeToPoints(ResourceType.REED)
                .resourceNumToPoints(new int[][]{{3,1},{5,2},{7,3}}).build());
        majorCards.add(8L);

        //바구니제작소
        cardDictionary.addCard(9L, MajorCard.builder()
                .cardID(9L)
                .bonusPoint(2)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(2).build()})
                .resourceToFoodHarvest(ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build())
                .resourceTypeToPoints(ResourceType.WOOD)
                .resourceNumToPoints(new int[][]{{3,1},{5,2},{7,3}}).build());
        majorCards.add(9L);

        //우물 (더미) 능력 없음
        cardDictionary.addCard(10L, MajorCard.builder()
                .cardID(10L)
                .bonusPoint(4)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(1).build(),
                        ResourceStruct.builder().resource(ResourceType.STONE).count(3).build()}).build());
        majorCards.add(10L);
    }
}
