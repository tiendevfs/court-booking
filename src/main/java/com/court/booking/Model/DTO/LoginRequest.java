package com.court.booking.Model.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotEmpty(message = "Vui lòng nhập tài khoản")
    private String username;

    @NotEmpty(message = "Vui lòng nhập mật khẩu")
    private String password;
}
