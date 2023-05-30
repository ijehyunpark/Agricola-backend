package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.dto.BuildFenceActionExtensionRequest;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import com.semoss.agricola.util.Pair;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: 울타리 건설 액션
 * 울타리를 건설해 봐요.
 * e.g 집, 외양간 여러개 짓기
 */
public class BuildFenceAction implements Action {
    @Getter
    private final ActionType actionType = ActionType.BUILD_FENCE;
    private final ResourceStruct requirements;
    @Builder
    public BuildFenceAction(ResourceStruct requirements) {
        this.requirements = requirements;
    }

    public void runAction(Player player, List<BuildFenceActionExtensionRequest> requestList) {
        int requestSize = requestList.size();
        // 자원 검증
        if(player.getResource(requirements.getResource()) < requestSize)
            throw new ResourceLackException();

        // 요청 변환
        List<Pair<Integer, Integer>> colList = new ArrayList<>();
        List<Pair<Integer, Integer>> rowList = new ArrayList<>();

        requestList.forEach(
                request -> {
                    if(request.isCol())
                        colList.add(new Pair<>(request.getY(), request.getX()));
                    else
                        rowList.add(new Pair<>(request.getY(), request.getX()));
                }
        );
        int colSize = colList.size();
        int rowSize = rowList.size();
        int [][] colListAdapter = new int[colSize][2];
        int [][] rowListAdapter = new int[rowSize][2];

        for (int i = 0; i < colSize; i++) {
            colListAdapter[i][0] = colList.get(i).first();
            colListAdapter[i][1] = colList.get(i).second();
        }

        for (int i = 0; i < rowSize; i++) {
            rowListAdapter[i][0] = rowList.get(i).first();
            rowListAdapter[i][1] = rowList.get(i).second();
        }


        // 울타리 건설
        player.buildFence(rowListAdapter, colListAdapter);

        // 자원 소모
        player.useResource(requirements.getResource(), requirements.getCount() * requestSize);
    }
}
