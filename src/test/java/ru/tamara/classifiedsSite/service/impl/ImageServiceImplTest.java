package ru.tamara.classifiedsSite.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.tamara.classifiedsSite.entity.Image;
import ru.tamara.classifiedsSite.exception.ImageNotFoundException;
import ru.tamara.classifiedsSite.repository.ImageRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.tamara.classifiedsSite.TestConstants.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    @DisplayName("Проверка сохранения картинки")
    public void testSaveImage() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenReturn(BYTES);

        Image savedImage = new Image();
        when(imageRepository.saveAndFlush(any(Image.class))).thenReturn(savedImage);

        Image result = imageService.saveImage(mockFile);

        verify(mockFile, times(1)).getBytes();
        verify(imageRepository, times(1)).saveAndFlush(any(Image.class));
        assertEquals(savedImage, result);
    }

    @Test
    @DisplayName("Проверка обновления картинки")
    public void testUpdateImage() throws  IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenReturn(BYTES);

        Image oldImage = new Image();
        oldImage.setId(IMAGE_ID);
        oldImage.setImage(OLD_BYTES);

        Image savedImage = new Image();
        savedImage.setId(IMAGE_ID);
        savedImage.setImage(BYTES);
        when(imageRepository.saveAndFlush(any(Image.class))).thenReturn(savedImage);

        Image result = imageService.updateImage(mockFile, oldImage);

        verify(mockFile, times(1)).getBytes();
        verify(imageRepository, times(1)).saveAndFlush(any(Image.class));
        assertArrayEquals(BYTES, oldImage.getImage());
        assertEquals(savedImage, result);

    }

    @Test
    @DisplayName("Проверка поиска картинки по id")
    public void testGetImageById() {
        Image image = new Image();
        image.setId(IMAGE_ID);
        image.setImage(BYTES);

        when(imageRepository.findById(IMAGE_ID)).thenReturn(Optional.of(image));
        byte[] result = imageService.getImageById(IMAGE_ID);
        assertEquals(BYTES, result);
    }

    @Test
    @DisplayName("Проверка поиска картинки по несуществующему id")
    public void testGetImageByIdNotFound() {
        when(imageRepository.findById(IMAGE_ID)).thenReturn(Optional.empty());

        assertThrows(ImageNotFoundException.class, () -> imageService.getImageById(IMAGE_ID));
        verify(imageRepository, times(1)).findById(IMAGE_ID);
    }
}
