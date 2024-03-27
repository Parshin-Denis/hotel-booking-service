package com.example.booking.dto;

import com.example.booking.model.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Необходимо ввести имя пользователя")
    private String name;

    @NotBlank(message = "Необходимо ввести пароль пользователя")
    private String password;

    @Email(message = "Неправильный формат электронной почты")
    private String email;

    private RoleType role;
}
