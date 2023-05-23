package com.semoss.agricola.GamePlay.domain.action.implement;

import java.util.Arrays;

public enum ActionName {
    ACTION1(1, "방 만들기 그리고/또는 외양간 짓기"),
    ACTION2(2, "시작 플레이어 되기 그리고/또는 보조 설비 1개 놓기"),
    ACTION3(3, "곡식 1개 가져가기"),
    ACTION4(4, "직업 1개 놓기"),
    ACTION5(5, "밭 1개 일구기"),
    ACTION6(6, "날품팔이"),
    ACTION7(7, "나무 3개"),
    ACTION8(8, "흙 1개"),
    ACTION9(9, "갈대 1개"),
    ACTION10(10, "낚시"),
    ACTION11(11, "양 1마리"),
    ACTION12(12, "울타리 치기"),
    ACTION13(13, "주요 설비나 보조 설비 1개 놓기"),
    ACTION14(14, "씨 뿌리기 그리고/또는 빵 굽기"),
    ACTION15(15, "가족 늘리기 한 후에 보조 설비 1개 놓기"),
    ACTION16(16, "돌 1개"),
    ACTION17(17, "집 고치기 한 후에 주요 설비나 보조 설비 1개 놓기"),
    ACTION18(18, "멧돼지 1마리"),
    ACTION19(19, "채소 1개 가져가기"),
    ACTION20(20, "소 1마리"),
    ACTION21(21, "돌 1개"),
    ACTION22(22, "밭 1개 일구기 그리고/또는 씨 뿌리기"),
    ACTION23(23, "빈 방이 없어도 가족 늘리기"),
    ACTION24(24, "집 고치기 한 후에 울타리 치기");

    private final int id;
    private final String name;

    ActionName(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public static ActionName getEventNameById(int id) {
        return Arrays.stream(ActionName.values())
                .filter(eventName -> eventName.getId() == id)
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

}
