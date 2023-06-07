package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.StackResource;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 라운드에 자원을 쌓아둡니다.
 */
@Getter
@Component
public class ReedCollector extends DefaultOccupation implements StackResource {
    private final ResourceStruct stackResource = ResourceStruct.builder().resource(ResourceType.REED).count(1).build();
    private final int[] rounds = new int[]{1,2,3,4};
    private final boolean isStaticRound = false;
    private final int playerRequirement;

    public ReedCollector(@Value("${reedCollector.id}") Long cardID,
                           @Value("${reedCollector.name}") String name,
                           @Value("${reedCollector.players}") Integer playerRequirement,
                           @Value("${reedCollector.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }

    @Override
    public void place(Player player, CardDictionary cardDictionary, int round) {
        super.place(player, cardDictionary, round);
        ArrayList<Integer> list = new ArrayList<>();

        // 플레이어의 라운드 스택에 자원 추가
        if (isStaticRound) {
            for (int targetRound : rounds) {
                if (targetRound > round) list.add(targetRound);
            }
        }
        else {
            for (int targetRound : rounds) {
                int sum = targetRound + round;
                if (sum > 14) break;
                list.add(sum);
            }
        }
        player.addRoundStack(list.stream().mapToInt(i -> i).toArray(),stackResource);
    }
}
