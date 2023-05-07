package com.semoss.agricola.GamePlay.domain.board;

import com.semoss.agricola.GamePlay.domain.majorImprovement.MajorImprovement;
import lombok.Getter;

import java.util.List;
@Getter
public class ImprovementBoard {
    private MajorImprovement[] majorCard;
    private List<Integer> owner;

}