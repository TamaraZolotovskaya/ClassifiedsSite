package ru.tamara.classifiedsSite.service.mapper;

import ru.tamara.classifiedsSite.dto.AdsDto;
import ru.tamara.classifiedsSite.dto.CreateAdsDto;
import ru.tamara.classifiedsSite.dto.FullAdsDto;
import ru.tamara.classifiedsSite.entity.Ad;

import java.util.Collection;

public interface AdMapper {

    AdsDto mapAdToAdDto(Ad ad);
    Ad mapAdsDtoToAd(AdsDto adsDto);
    FullAdsDto mapAdToFullAdsDTo(Ad ad);
    Ad mapCreatedAdsDtoToAd(CreateAdsDto createAdsDto);
    Collection<AdsDto> mapAdListToAdDtoList(Collection<Ad> adCollection);

}

