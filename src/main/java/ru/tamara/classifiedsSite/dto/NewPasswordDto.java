package ru.tamara.classifiedsSite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для передачи данных при смене пароля пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordDto {
    //текущий пароль
    private String currentPassword;
    //новый пароль
    private String newPassword;
}
