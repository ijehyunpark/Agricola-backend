package com.semoss.agricola.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperSingleton {
    private static final ObjectMapper objectMapper;

    static {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        objectMapper = builder
                .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }
}
