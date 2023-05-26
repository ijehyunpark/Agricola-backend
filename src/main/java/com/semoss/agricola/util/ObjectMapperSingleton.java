package com.semoss.agricola.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ObjectMapperSingleton() {
        // private 생성자로 외부에서의 인스턴스 생성 방지
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }
}
