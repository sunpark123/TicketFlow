package com.sunpark.ticketflow.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class VerificationDTO {
    @NotBlank(message = "아이디를 입력해주세요")
    private String userId;

    @NotBlank(message = "번호를 입력해주세요")
    private String phone;

    private String code;
}
