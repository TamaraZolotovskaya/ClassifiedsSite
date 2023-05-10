package ru.tamara.classifiedsSite.service.impl;

import org.springframework.stereotype.Service;
import ru.tamara.classifiedsSite.entity.Image;
import ru.tamara.classifiedsSite.exception.ImageNotFoundException;
import ru.tamara.classifiedsSite.repository.AdRepository;
import ru.tamara.classifiedsSite.repository.ImageRepository;
import ru.tamara.classifiedsSite.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final AdRepository adRepository;

    public ImageServiceImpl(ImageRepository imageRepository, AdRepository adRepository) {
        this.imageRepository = imageRepository;
        this.adRepository = adRepository;
    }

    @Override
    public byte[] getImagePathById(String id) {
        Image image = imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
        return image.getImagePath();
    }
}
