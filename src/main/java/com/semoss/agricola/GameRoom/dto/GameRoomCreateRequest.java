    package com.semoss.agricola.GameRoom.dto;

    import jakarta.validation.constraints.Max;
    import jakarta.validation.constraints.Min;
    import jakarta.validation.constraints.NotNull;
    import lombok.*;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public class GameRoomCreateRequest {
        @NotNull(message = "name 필드는 필수입니다.")
        private String name;

        @Max(value = 4, message = "현재 4인용만 지원합니다.")
        @Min(value = 4, message = "현재 4인용만 지원합니다.")
        private Integer capacity = 4;
    }
