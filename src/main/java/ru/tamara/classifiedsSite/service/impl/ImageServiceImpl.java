package ru.tamara.classifiedsSite.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tamara.classifiedsSite.entity.Image;
import ru.tamara.classifiedsSite.exception.ImageNotFoundException;
import ru.tamara.classifiedsSite.repository.ImageRepository;
import ru.tamara.classifiedsSite.service.ImageService;

import java.io.IOException;
import java.util.UUID;

/**
 * Класс - сервис, содержащий реализацию интерфейса {@link ImageService}
 *
 * @see Image
 * @see ImageRepository
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    /**
     * Метод сохраняет картинку в базу данных
     *
     * @param image
     * @return {@link ru.tamara.classifiedsSite.repository.ImageRepository#saveAndFlush(Object)}
     */
    @Override
    public Image saveImage(MultipartFile image) {
        Image newImage = new Image();
        try {
            byte[] bytes = image.getBytes();
            newImage.setImage(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newImage.setId(UUID.randomUUID().toString());
        return imageRepository.saveAndFlush(newImage);
    }

    /**
     * Метод обновляет картинку в базе данных
     *
     * @param newImage
     * @param oldImage
     * @return {@link ru.tamara.classifiedsSite.repository.ImageRepository#saveAndFlush(Object)}
     */
    @Override
    public Image updateImage(MultipartFile newImage, Image oldImage) {
        try {
            byte[] bytes = newImage.getBytes();
            oldImage.setImage(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageRepository.saveAndFlush(oldImage);
    }

    /**
     * Метод достает картинку из базы данных по ее id {@link ru.tamara.classifiedsSite.repository.ImageRepository#findById(Object)}
     *
     * @param id
     * @throws ru.tamara.classifiedsSite.exception.ImageNotFoundException
     * @return {@link Image#getImage()}
     */
    @Override
    public byte[] getImageById(String id) {
        Image image = imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
        return image.getImage();
    }
}
