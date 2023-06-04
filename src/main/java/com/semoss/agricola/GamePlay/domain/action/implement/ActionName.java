package com.semoss.agricola.GamePlay.domain.action.implement;

import java.util.Arrays;

public enum ActionName {
    ACTION1(1, "농장 확장"),
    ACTION2(2, "회합 장소"),
    ACTION3(3, "곡식 종자"),
    ACTION4(4, "교습"),
    ACTION5(5, "농지"),
    ACTION6(6, "날품팔이"),
    ACTION7(7, "숲"),
    ACTION8(8, "흙 채굴장"),
    ACTION9(9, "갈대밭"),
    ACTION10(10, "낚시"),
    ACTION11(11, "양 시장"),
    ACTION12(12, "울타리"),
    ACTION13(13, "주요 설비"),
    ACTION14(14, "곡식 활용"),
    ACTION15(15, "기본 가족 늘리기"),
    ACTION16(16, "서부 채석장"),
    ACTION17(17, "집 개조"),
    ACTION18(18, "돼지 시장"),
    ACTION19(19, "채소 종자"),
    ACTION20(20, "소 시장"),
    ACTION21(21, "동부 채석장"),
    ACTION22(22, "밭 농사"),
    ACTION23(23, "급한 가족 늘리기"),
    ACTION24(24, "농장 개조"),
    ACTION25(25, "덤블"),
    ACTION26(26, "수풀"),
    ACTION27(27, "자원 시장"),
    ACTION28(28, "점토 채굴장"),
    ACTION29(29, "교습"),
    ACTION30(30, "유량극단");




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
