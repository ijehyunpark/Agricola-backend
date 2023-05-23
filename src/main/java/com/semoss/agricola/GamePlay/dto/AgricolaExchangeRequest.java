package com.semoss.agricola.GamePlay.dto;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AgricolaExchangeRequest {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ExchangeFormat {
        @NotNull(message = "필수 입력")
        private Long improvementId;
        @NotNull(message = "필수 입력")
        private ResourceStruct resource;
    }
    @NotNull(message = "필수 입력")
    private List<ExchangeFormat> exchange;
}
