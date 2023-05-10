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
import ru.tamara.classifiedsSite.repository.ImageRepository;
import ru.tamara.classifiedsSite.service.AdService;
import ru.tamara.classifiedsSite.service.UserService;
import ru.tamara.classifiedsSite.service.mapper.AdMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final AdMapper adMapper;

    /**
     * Метод проверяет наличие доступа к объявлению по id
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

    @Override
    public ResponseWrapperAdsDto getAllAdsDto() {
        Collection<AdsDto> adsAll = adMapper.mapAdListToAdDtoList(adRepository.findAll());
        return new ResponseWrapperAdsDto(adsAll);
    }

    @Override
    public AdsDto createAds(CreateAdsDto adDto, MultipartFile image) {
        Ad newAd = adMapper.mapCreatedAdsDtoToAd(adDto);
        newAd.setAuthor(userService.findAuthUser().orElseThrow(UserNotFoundException::new));
        Image newImage = new Image();
        try {
            byte[] bytes = image.getBytes();
            newImage.setImagePath(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newImage.setId(UUID.randomUUID().toString());
        Image savedImage = imageRepository.saveAndFlush(newImage);
        newAd.setImage(savedImage);
        adRepository.save(newAd);
        return adMapper.mapAdToAdDto(newAd);
    }

    @Override
    public FullAdsDto getFullAdDto(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        return adMapper.mapAdToFullAdsDTo(ad);
    }

    @Override
    public boolean removeAdDto(Integer id) {
        if (checkAccess(id)) {
            adRepository.deleteById(id);
            return true;
        }
        throw new UserForbiddenException();
    }

    @Override
    public AdsDto updateAdDto(Integer id, CreateAdsDto createAdsDto) {
        Ad ad = adRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        if (checkAccess(id)) {
            ad.setDescription(createAdsDto.getDescription());
            ad.setPrice(createAdsDto.getPrice());
            ad.setTitle(createAdsDto.getTitle());
            return adMapper.mapAdToAdDto(adRepository.save(ad));
        }
        throw new UserForbiddenException();
    }

    @Override
    public ResponseWrapperAdsDto getAllUserAdsDto() {
        User user = userService.findAuthUser().orElseThrow(UserNotFoundException::new);
        Collection<Ad> allAds = adRepository.findAll();
        Collection<Ad> userAds = allAds.stream().filter(x -> x.getAuthor().equals(user)).collect(Collectors.toList());
        return new ResponseWrapperAdsDto(adMapper.mapAdListToAdDtoList(userAds));
    }

    @Override
    public void updateImageAdDto(Integer id, MultipartFile image) {
        Ad ad = adRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        Image oldImage = ad.getImage();
        try {
            byte[] bytes = image.getBytes();
            oldImage.setImagePath(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image savedImage = imageRepository.saveAndFlush(oldImage);
        ad.setImage(savedImage);
        adRepository.save(ad);
    }
}
