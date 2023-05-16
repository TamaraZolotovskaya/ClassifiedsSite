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
        byte[] mockBytes = new byte[] {0, 1, 2};
        when(mockFile.getBytes()).thenReturn(mockBytes);

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
        byte[] mockBytes = new byte[] {0, 1, 2};
        when(mockFile.getBytes()).thenReturn(mockBytes);

        Image oldImage = new Image();
        oldImage.setId("old-id");
        byte[] oldBytes = new byte[] {3, 4, 5};
        oldImage.setImage(oldBytes);

        Image savedImage = new Image();
        savedImage.setId("old-id");
        savedImage.setImage(mockBytes);
        when(imageRepository.saveAndFlush(any(Image.class))).thenReturn(savedImage);

        Image result = imageService.updateImage(mockFile, oldImage);

        verify(mockFile, times(1)).getBytes();
        verify(imageRepository, times(1)).saveAndFlush(any(Image.class));

        assertArrayEquals(mockBytes, oldImage.getImage());
        assertEquals(savedImage, result);

    }

    @Test
    @DisplayName("Проверка поиска картинки по id")
    public void testGetImageById() {
        String id = "test-id";
        byte[] bytes = new byte[] {0, 1, 2};

        Image image = new Image();
        image.setId(id);
        image.setImage(bytes);

        when(imageRepository.findById(id)).thenReturn(Optional.of(image));

        byte[] result = imageService.getImageById(id);

        assertEquals(bytes, result);
    }

    @Test
    @DisplayName("Проверка поиска картинки по несуществующему id")
    public void testGetImageByIdNotFound() {
        String id = "not-found-id";

        when(imageRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ImageNotFoundException.class, () -> imageService.getImageById(id));

        verify(imageRepository, times(1)).findById(id);
    }
}
