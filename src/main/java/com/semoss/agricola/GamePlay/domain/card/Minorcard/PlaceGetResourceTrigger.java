package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;

/**
 * 원래는 place 할 때 왼쪽 유저에게 카드를 넘겨야하는 카드들입니다.
 * 그러나 프론트쪽이랑 얘기되지 않은게 갑자기 추가되는 것이고(손에 들고있는 카드 최대 개수가 있었던 것 같습니다)
 * place 메소드를 오버라이딩 하기에는 card dictionary 를 이 행동할 때만 추가로 받아야해서 일단 구현을 보류했습니다.
 *
 * 카드를 내려놓았을 때 자원 획득
 */
public interface PlaceGetResourceTrigger extends MinorCard {
    ResourceStruct getBonusResource();
}
