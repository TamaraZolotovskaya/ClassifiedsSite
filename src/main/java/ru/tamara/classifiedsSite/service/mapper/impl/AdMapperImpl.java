package ru.tamara.classifiedsSite.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.tamara.classifiedsSite.dto.AdsDto;
import ru.tamara.classifiedsSite.dto.CreateAdsDto;
import ru.tamara.classifiedsSite.dto.FullAdsDto;
import ru.tamara.classifiedsSite.entity.Ad;
import ru.tamara.classifiedsSite.service.mapper.AdMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class AdMapperImpl implements AdMapper {

    @Override
    public AdsDto mapAdToAdDto(Ad ad) {
        AdsDto adsDto = new AdsDto();
        adsDto.setPk(ad.getId());
        adsDto.setAuthor(ad.getAuthor().getId());
        adsDto.setPrice(ad.getPrice());
        adsDto.setImage("/ads/" + ad.getImage().getId() + "/image");
        adsDto.setTitle(ad.getTitle());
        return adsDto;
    }

    @Override
    public Ad mapAdsDtoToAd(AdsDto adsDto) {
        Ad mappedAd = new Ad();
        mappedAd.setId(adsDto.getPk());
        mappedAd.getAuthor().setId(adsDto.getAuthor());
        mappedAd.setPrice(adsDto.getPrice());
        mappedAd.getImage().setId(adsDto.getImage());
        mappedAd.setTitle(adsDto.getTitle());
        return mappedAd;
    }

    @Override
    public FullAdsDto mapAdToFullAdsDTo(Ad ad) {
        FullAdsDto fullAdsDto = new FullAdsDto();
        fullAdsDto.setPk(ad.getId());
        fullAdsDto.setAuthorFirstName(ad.getAuthor().getFirstName());
        fullAdsDto.setAuthorLastName(ad.getAuthor().getLastName());
        fullAdsDto.setEmail(ad.getAuthor().getEmail());
        fullAdsDto.setPhone(ad.getAuthor().getPhone());
        fullAdsDto.setTitle(ad.getTitle());
        fullAdsDto.setDescription(ad.getDescription());
        fullAdsDto.setImage("/ads/" + ad.getImage().getId() + "/image");
        fullAdsDto.setPrice(ad.getPrice());
        return fullAdsDto;
    }

    @Override
    public Ad mapCreatedAdsDtoToAd(CreateAdsDto createAdsDto) {
        Ad ad = new Ad();
        ad.setTitle(createAdsDto.getTitle());
        ad.setDescription(createAdsDto.getDescription());
        ad.setPrice(createAdsDto.getPrice());
        return ad;
    }

    @Override
    public Collection<AdsDto> mapAdListToAdDtoList(Collection<Ad> adCollection) {
        List<AdsDto> dtoList = new ArrayList<AdsDto>(adCollection.size());
        for (Ad ad : adCollection) {
            dtoList.add(mapAdToAdDto(ad));
        }
        return dtoList;
    }
}