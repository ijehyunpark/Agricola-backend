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
    public static class ExchangeFormat {
        @NotNull(message = "필수 입력")
        private String improvementId;
        @NotNull(message = "필수 입력")
        private ResourceStruct resource;
    }
    @NotNull(message = "필수 입력")
    private List<ExchangeFormat> exchange;
}
