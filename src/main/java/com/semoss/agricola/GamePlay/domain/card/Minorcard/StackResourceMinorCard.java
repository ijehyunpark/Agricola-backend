package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class StackResourceMinorCard extends DefaultMinorCard implements StackResource{
    private final ResourceStruct stackResource;
    private final int[] rounds;
    private final boolean isStaticRound;

    @Builder
    StackResourceMinorCard(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, ResourceStruct stackResource, int[] rounds, boolean isStaticRound) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum);
        this.stackResource = stackResource;
        this.rounds = rounds;
        this.isStaticRound = isStaticRound;
    }

    @Override
    public void place(Player player) {
        super.place(player);
        ArrayList<Integer> list = new ArrayList<>();

        // 플레이어의 라운드 스택에 자원 추가
        if (isStaticRound) {
            int now = player.getGame().getRound();
            for (int round : rounds) {
                if (round > now) list.add(round);
            }
        }
        else {
            for (int round : rounds) {
                int sum = round + player.getGame().getRound();
                if (sum > 14) break;
                list.add(sum);
            }
        }
        player.addRoundStack(list.stream().mapToInt(i -> i).toArray(),stackResource);
    }
}
