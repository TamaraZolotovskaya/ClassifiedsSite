package ru.tamara.classifiedsSite.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tamara.classifiedsSite.dto.*;
import ru.tamara.classifiedsSite.entity.Ad;
import ru.tamara.classifiedsSite.entity.Image;
import ru.tamara.classifiedsSite.entity.User;
import ru.tamara.classifiedsSite.exception.AdsNotFoundException;
import ru.tamara.classifiedsSite.exception.UserForbiddenException;
import ru.tamara.classifiedsSite.exception.UserNotFoundException;
import ru.tamara.classifiedsSite.repository.AdRepository;
import ru.tamara.classifiedsSite.service.AdService;
import ru.tamara.classifiedsSite.service.ImageService;
import ru.tamara.classifiedsSite.service.UserService;
import ru.tamara.classifiedsSite.service.mapper.AdMapper;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final AdMapper adMapper;

    /**
     * Метод ищет и возвращает список всех объявлений
     *
     * @return ResponseWrapperAdDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    @Override
    public ResponseWrapperAdDto getAllAdsDto() {
        Collection<AdDto> adsAll = adMapper.mapAdListToAdDtoList(adRepository.findAll());
        return new ResponseWrapperAdDto(adsAll);
    }

    /**
     * Метод создает объявление
     *
     * @param adDto
     * @param image
     * @return AdDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    @Override
    public AdDto createAds(CreateAdDto adDto, MultipartFile image) {
        Ad newAd = adMapper.mapCreatedAdsDtoToAd(adDto);
        newAd.setAuthor(userService.findAuthUser().orElseThrow(UserNotFoundException::new));
        Image newImage = imageService.saveImage(image);
        newAd.setImage(newImage);
        adRepository.save(newAd);
        return adMapper.mapAdToAdDto(newAd);
    }

    /**
     * Метод ищет и возвращает объявление по id
     *
     * @param id
     * @return FullAdDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    @Override
    public FullAdDto getFullAdDto(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        return adMapper.mapAdToFullAdsDTo(ad);
    }

    /**
     * Метод удаляет объявление по id
     *
     * @param id
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    @Override
    public boolean removeAdDto(Integer id) {
        if (checkAccess(id)) {
            adRepository.deleteById(id);
            return true;
        }
        throw new UserForbiddenException();
    }

    /**
     * Метод редактирует объявление по id
     *
     * @param id
     * @param createAdDto
     * @return AdDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    @Override
    public AdDto updateAdDto(Integer id, CreateAdDto createAdDto) {
        Ad ad = adRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        if (checkAccess(id)) {
            ad.setDescription(createAdDto.getDescription());
            ad.setPrice(createAdDto.getPrice());
            ad.setTitle(createAdDto.getTitle());
            return adMapper.mapAdToAdDto(adRepository.save(ad));
        }
        throw new UserForbiddenException();
    }

    /**
     * Метод ищет и возвращает список всех объявлений авторизированного пользователя
     *
     * @return ResponseWrapperAdDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    @Override
    public ResponseWrapperAdDto getAllUserAdsDto() {
        User user = userService.findAuthUser().orElseThrow(UserNotFoundException::new);
        Collection<Ad> allAds = adRepository.findAll();
        Collection<Ad> userAds = allAds.stream().filter(x -> x.getAuthor().equals(user)).collect(Collectors.toList());
        return new ResponseWrapperAdDto(adMapper.mapAdListToAdDtoList(userAds));
    }

    /**
     * Метод обновляет изображение к объявлению по id
     *
     * @param id
     * @param image
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    @Override
    public void updateImageAdDto(Integer id, MultipartFile image) {
        Ad ad = adRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        Image updatedImage = imageService.updateImage(image, ad.getImage());
        ad.setImage(updatedImage);
        adRepository.save(ad);
    }

    /**
     * Метод проверяет наличие доступа к объявлению по id
     *
     * @param id
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    @Override
    public boolean checkAccess(Integer id) {
        Role role = Role.ADMIN;
        Ad ad = adRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        Optional<User> user = userService.findAuthUser();
        User notOptionalUser = user.get();
        String currentPrincipalName = notOptionalUser.getUsername();
        return ad.getAuthor().getUsername().equals(currentPrincipalName)
                || notOptionalUser.getAuthorities().contains(role);
    }
}
