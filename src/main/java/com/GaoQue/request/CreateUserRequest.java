package com.GaoQue.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;

    @NotNull(message = "Mật khẩu không được để trống.")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Mật khẩu phải chứa ít nhất một chữ cái in hoa, một chữ số và một ký tự đặc biệt.")
    private String password;

    private String role;
}
