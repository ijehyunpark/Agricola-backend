package com.semoss.agricola.GamePlay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AgricolaActionRequest {
    @NotNull(message = "eventId는 필수 입니다.")
    private Long eventId;

    private List<Object> acts;
}
