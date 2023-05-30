package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.card.Minorcard.StackResource;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Well extends DefaultMajorCard implements MajorCard, StackResource {
    // 해당 라운드 +stackRounds 에 자원을 쌓음
    private final int[] rounds;
    private final ResourceStruct stackResource;
    private final boolean isStaticRound;

    @Builder
    Well(Long cardID, int bonusPoint, ResourceStruct[] ingredients, int[] rounds, ResourceStruct stackResource, boolean isStaticRound) {
        super(cardID, bonusPoint, ingredients);
        this.rounds = rounds;
        this.stackResource = stackResource;
        this.isStaticRound = isStaticRound;
    }

    @Override
    public void place(Player player) {
        super.place(player);
        int nowRound = player.getGame().getGameState().getRound();
        int sum;
        ArrayList<Integer> list = new ArrayList<>();

        for (int stackRound : rounds) {
            sum = stackRound + nowRound;
            if (sum > 14) {
                break;
            }
            list.add(sum);
        }
        player.addRoundStack(list.stream().mapToInt(i -> i).toArray(), stackResource);
    }

    @Override
    public boolean hasBakeMajorTrigger() {
        return false;
    }

    @Override
    public boolean hasCookingAnytimeTrigger() {
        return false;
    }

    @Override
    public boolean hasCookingHarvestTrigger() {
        return false;
    }
}
