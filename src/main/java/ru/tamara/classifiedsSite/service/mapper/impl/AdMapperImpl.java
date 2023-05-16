package ru.tamara.classifiedsSite.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.tamara.classifiedsSite.dto.AdDto;
import ru.tamara.classifiedsSite.dto.CreateAdDto;
import ru.tamara.classifiedsSite.dto.FullAdDto;
import ru.tamara.classifiedsSite.entity.Ad;
import ru.tamara.classifiedsSite.service.mapper.AdMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Класс - сервис-маппер, содержащий реализацию интерфейса {@link AdMapper}
 */
@Component
public class AdMapperImpl implements AdMapper {
    @Override
    public AdDto mapAdToAdDto(Ad ad) {
        AdDto adDto = new AdDto();
        adDto.setPk(ad.getId());
        adDto.setAuthor(ad.getAuthor().getId());
        adDto.setPrice(ad.getPrice());
        adDto.setImage("/ads/" + ad.getImage().getId() + "/image");
        adDto.setTitle(ad.getTitle());
        return adDto;
    }

    @Override
    public Ad mapAdsDtoToAd(AdDto adDto) {
        Ad mappedAd = new Ad();
        mappedAd.setId(adDto.getPk());
        mappedAd.getAuthor().setId(adDto.getAuthor());
        mappedAd.setPrice(adDto.getPrice());
        mappedAd.getImage().setId(adDto.getImage());
        mappedAd.setTitle(adDto.getTitle());
        return mappedAd;
    }

    @Override
    public FullAdDto mapAdToFullAdsDTo(Ad ad) {
        FullAdDto fullAdDto = new FullAdDto();
        fullAdDto.setPk(ad.getId());
        fullAdDto.setAuthorFirstName(ad.getAuthor().getFirstName());
        fullAdDto.setAuthorLastName(ad.getAuthor().getLastName());
        fullAdDto.setEmail(ad.getAuthor().getEmail());
        fullAdDto.setPhone(ad.getAuthor().getPhone());
        fullAdDto.setTitle(ad.getTitle());
        fullAdDto.setDescription(ad.getDescription());
        fullAdDto.setImage("/ads/" + ad.getImage().getId() + "/image");
        fullAdDto.setPrice(ad.getPrice());
        return fullAdDto;
    }

    @Override
    public Ad mapCreatedAdsDtoToAd(CreateAdDto createAdDto) {
        Ad ad = new Ad();
        ad.setTitle(createAdDto.getTitle());
        ad.setDescription(createAdDto.getDescription());
        ad.setPrice(createAdDto.getPrice());
        return ad;
    }

    @Override
    public Collection<AdDto> mapAdListToAdDtoList(Collection<Ad> adCollection) {
        List<AdDto> dtoList = new ArrayList<AdDto>(adCollection.size());
        for (Ad ad : adCollection) {
            dtoList.add(mapAdToAdDto(ad));
        }
        return dtoList;
    }
}