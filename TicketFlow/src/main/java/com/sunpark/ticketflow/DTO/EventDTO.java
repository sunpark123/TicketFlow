package com.sunpark.ticketflow.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
public class EventDTO {
    @NotBlank(message = "이벤트 제목을 입력해주세요")
    private String title;

    @NotBlank(message = "이벤트 내용을 입력해주세요")
    private String description;

    @NotBlank(message = "이벤트 카테고리를 입력해주세요")
    private String category;

    @NotNull(message = "이벤트 구매 시작 시간을 입력해주세요")
    private LocalDateTime booking_start_at;

    @NotNull(message = "이벤트 구매 종료 시간을 입력해주세요")
    private LocalDateTime booking_end_at;
}
