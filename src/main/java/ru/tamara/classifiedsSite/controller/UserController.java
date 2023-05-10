package ru.tamara.classifiedsSite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tamara.classifiedsSite.dto.NewPasswordDto;
import ru.tamara.classifiedsSite.dto.UserDto;
import ru.tamara.classifiedsSite.service.AuthService;
import ru.tamara.classifiedsSite.service.ImageService;
import ru.tamara.classifiedsSite.service.UserService;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final ImageService imageService;

    @Operation(
            summary = "Обновление пароля",
            description = "Изменение пароля пользователя из тела запроса"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Установлен новый пароль"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content()}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content()}),
    }
    )
    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPasswordDto) {
        authService.changePassword(newPasswordDto);
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Получить информацию об авторизованном пользователе"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Данные пользователя получены",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                            )
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content()})
    }
    )
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser() {
        UserDto currentUserDto = userService.getUserDto();
        return ResponseEntity.ok().body(currentUserDto);
    }

    @Operation(
            summary = "Обновить информацию об авторизованном пользователе",
            description = "Обновление данных пользователя из тела запроса"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK. Данные пользователя обновлены",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                            )
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content()})
    }
    )
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        UserDto newUserDto = userService.updateUserDto(userDto);
        return ResponseEntity.ok().body(newUserDto);
    }

    @Operation(
            summary = "Обновить аватар авторизованного пользователя",
            description = "Обновление изображения пользователя из тела запроса"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Изображение пользователя обновлено", content = {@Content()}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content()})
    })
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateUserImage(@RequestPart MultipartFile image) {
        userService.updateUserImage(image);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить аватар пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content())
            })
    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        return ResponseEntity.ok(imageService.getImagePathById(id));
    }
}
