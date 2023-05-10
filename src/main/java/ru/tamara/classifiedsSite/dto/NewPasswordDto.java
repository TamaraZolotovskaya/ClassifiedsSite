package ru.tamara.classifiedsSite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordDto {
    //текущий пароль
    private String currentPassword;
    //новый пароль
    private String newPassword;
}
