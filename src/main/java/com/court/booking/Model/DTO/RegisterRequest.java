package com.court.booking.Model.DTO;

import com.court.booking.utils.MatchPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@MatchPassword
public class RegisterRequest {
    @Size(min = 6, message = "Cần ít nhất 6 kí tự cho username")
    private String username;

    @Email(message = "Nhập đúng định dạng email")
    private String email;

    @Size(min = 6, message = "Cần ít nhất 6 kí tự cho họ tên")
    private String fullname;

    @Size(min = 6, message = "Cần ít nhất 6 kí tự cho mật khẩu")
    private String password;

    private String confirmPassword;

}
