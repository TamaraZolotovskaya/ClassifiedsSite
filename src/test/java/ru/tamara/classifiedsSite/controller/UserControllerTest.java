package ru.tamara.classifiedsSite.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import ru.tamara.classifiedsSite.dto.NewPasswordDto;
import ru.tamara.classifiedsSite.dto.UserDto;
import ru.tamara.classifiedsSite.service.AuthService;
import ru.tamara.classifiedsSite.service.ImageService;
import ru.tamara.classifiedsSite.service.UserService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private AuthService authService;
    @Mock
    private UserService userService;
    @Mock
    private ImageService imageService;
    private final String USERNAME = "testUser";
    private final UserDto userDto = new UserDto();

    @BeforeEach
    public void setUp() {
        userDto.setEmail(USERNAME);
    }

    @Test
    public void setPasswordTest() {
        NewPasswordDto passwordDto = new NewPasswordDto();
        passwordDto.setCurrentPassword("oldpassword");
        passwordDto.setNewPassword("newpassword");

        ResponseEntity<?> responseEntity = userController.setPassword(passwordDto);

        verify(authService).changePassword(passwordDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getUserTest() {
        when(userService.getUserDto()).thenReturn(userDto);

        ResponseEntity<UserDto> responseEntity = userController.getUser();

        verify(userService).getUserDto();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(USERNAME, responseEntity.getBody().getEmail());
    }

    @Test
    public void updateUserTest() {
        when(userService.updateUserDto(userDto)).thenReturn(userDto);

        ResponseEntity<UserDto> responseEntity = userController.updateUser(userDto);

        verify(userService).updateUserDto(userDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(USERNAME, responseEntity.getBody().getEmail());
    }

    @Test
    public void updateUserImageTest() throws IOException {
        MockMultipartFile image = new MockMultipartFile("image", "image.png", "image/png",
                "test image".getBytes());

        doNothing().when(userService).updateUserImage(image);

        ResponseEntity<byte[]> responseEntity = userController.updateUserImage(image);

        verify(userService).updateUserImage(image);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getUserImageTest() {
        byte[] byteData = "test data".getBytes();

        when(imageService.getImageById("id")).thenReturn(byteData);

        ResponseEntity<byte[]> responseEntity = userController.getImage("id");

        verify(imageService).getImageById("id");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertArrayEquals(byteData, responseEntity.getBody());
    }
}
