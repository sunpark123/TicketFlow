package com.sunpark.ticketflow.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ReservationDTO {
    @NotNull(message = "이벤트 아이디 입력을 해주세요")
    private Integer eventId;

    @NotNull(message = "좌석 아이디 입력을 해주세요")
    private Integer seatNumber;

    private String userId;
}
