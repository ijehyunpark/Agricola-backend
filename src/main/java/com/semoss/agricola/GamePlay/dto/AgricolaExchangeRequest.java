package com.semoss.agricola.GamePlay.dto;

import com.semoss.agricola.GamePlay.domain.resource.AnimalType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AgricolaExchangeRequest {

    @NotNull(message = "필수 입력")
    private Long improvementId;

    private ResourceType resource;
    private AnimalType animal;

    @NotNull(message = "필수 입력")
    @Min(value = 1, message = "최소 1개 입력")
    private Long count;
}
