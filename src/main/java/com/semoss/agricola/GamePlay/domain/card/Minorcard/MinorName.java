package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import java.util.Arrays;

public enum MinorName {
    MINOR1(17, "풍차"),
    MINOR2(20, "간이 화로"),
    MINOR3(23, "관리소"),
    MINOR4(24, "동물 우리"),
    MINOR5(30, "카누"),
    MINOR6(35, "옥수수 국자"),
    MINOR7(39, "시장 마구간"),
    MINOR8(43, "과일 나무"),
    MINOR9(46, "자루 카트"),
    MINOR10(48, "갈대 연못"),
    MINOR11(74, "곡물 카트"),
    MINOR12(104, "주간시장"),
    MINOR13(110, "양조장");

    private final int id;
    private final String name;

    MinorName(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public static MinorName getMinorNameById(int id) {
        return Arrays.stream(MinorName.values())
                .filter(eventName -> eventName.getId() == id)
                .findAny()
                .orElseThrow(RuntimeException::new);
    }
}
