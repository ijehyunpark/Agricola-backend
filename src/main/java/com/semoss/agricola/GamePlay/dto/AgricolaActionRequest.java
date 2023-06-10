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

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ActionFormat {
        @NotNull(message = "필수 입력")
        private Boolean use;
        private Object acts;
    }

    private List<ActionFormat> acts;
}
