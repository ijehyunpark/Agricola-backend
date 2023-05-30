package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import java.util.Arrays;

public enum MajorName {
    MAJOR1(1, "화로"),
    MAJOR2(2, "화로"),
    MAJOR3(3, "화덕"),
    MAJOR4(4, "화덕"),
    MAJOR5(5, "흙가마"),
    MAJOR6(6, "돌가마"),
    MAJOR7(7, "가구제작소"),
    MAJOR8(8, "그릇제작소"),
    MAJOR9(9, "바구니제작소"),
    MAJOR10(10, "우물");

    private final int id;
    private final String name;

    MajorName(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public static MajorName getMajorNameById(int id) {
        return Arrays.stream(MajorName.values())
                .filter(eventName -> eventName.getId() == id)
                .findAny()
                .orElseThrow(RuntimeException::new);
    }


}
