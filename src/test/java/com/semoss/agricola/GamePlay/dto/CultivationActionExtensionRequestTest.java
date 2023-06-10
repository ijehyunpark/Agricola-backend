package com.semoss.agricola.GamePlay.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.util.ObjectMapperSingleton;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CultivationActionExtensionRequestTest {
    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    @Test
    @DisplayName("씨앗 심기 행동 역직렬화 테스트")
    void deserializeTest() throws JsonProcessingException {
        // given
        String json = "{\"y\":5,\"x\":10,\"resourceType\": \"GRAIN\"}";

        // when
        CultivationActionExtensionRequest request = mapper.readValue(json, CultivationActionExtensionRequest.class);

        // then
        Assertions.assertEquals(5, request.getY());
        Assertions.assertEquals(10, request.getX());
        Assertions.assertEquals(ResourceType.GRAIN, request.getResourceType());
    }

    @Test
    @DisplayName("씨앗 심기 행동 역직렬화 테스트: 한글도 됨!")
    void deserializeTest2() throws JsonProcessingException {
        // given
        String json = "{\"y\":5,\"x\":10,\"resourceType\": \"곡식\"}";

        // when
        CultivationActionExtensionRequest request = mapper.readValue(json, CultivationActionExtensionRequest.class);

        // then
        Assertions.assertEquals(5, request.getY());
        Assertions.assertEquals(10, request.getX());
        Assertions.assertEquals(ResourceType.GRAIN, request.getResourceType());
    }

    @Test
    @DisplayName("씨앗 심기 행동 대소문자 역직렬화 테스트")
    void deserializeTest_lowercase() throws JsonProcessingException {
        // given
        String json = "{\"y\":5,\"x\":10,\"resourceType\": \"grain\"}";

        // when
        CultivationActionExtensionRequest request = mapper.readValue(json, CultivationActionExtensionRequest.class);

        // then
        Assertions.assertEquals(5, request.getY());
        Assertions.assertEquals(10, request.getX());
        Assertions.assertEquals(ResourceType.GRAIN, request.getResourceType());
    }

    @Test
    @DisplayName("씨앗 심기 행동 대소문자 역직렬화 테스트")
    void deserializeTest_lowercase123() throws JsonProcessingException {
        // given
        String json = "{\"y\":5,\"x\":10,\"resourceType\": \"grain\"}";

        // when
        CultivationActionExtensionRequest request = mapper.readValue(json, CultivationActionExtensionRequest.class);

        // then
        Assertions.assertEquals(5, request.getY());
        Assertions.assertEquals(10, request.getX());
        Assertions.assertEquals(ResourceType.GRAIN, request.getResourceType());
    }
}