package com.sunpark.ticketflow.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "아이디를 입력해주세요")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
        message = "비밀번호는 영문, 숫자, 특수문자 포함 8~20자예요"
    )
    private String password;
}
