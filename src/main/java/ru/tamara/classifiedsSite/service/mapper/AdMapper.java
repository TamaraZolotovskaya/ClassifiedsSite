package ru.tamara.classifiedsSite.service.mapper;

import ru.tamara.classifiedsSite.dto.AdDto;
import ru.tamara.classifiedsSite.dto.CreateAdDto;
import ru.tamara.classifiedsSite.dto.FullAdDto;
import ru.tamara.classifiedsSite.entity.Ad;

import java.util.Collection;

/**
 * Интерфейс сервисного класса-маппера AdMapperImpl, для маппинга объектов Ad, AdDto и CreateAdDto
 *
 * @see ru.tamara.classifiedsSite.entity.Ad
 * @see AdDto
 * @see CreateAdDto
 * @see ru.tamara.classifiedsSite.service.mapper.impl.AdMapperImpl
 */
public interface AdMapper {
    /**
     * Метод, преобразующий объект класса Ad в объект класса AdDto.
     *
     * @param ad
     * @return AdDto
     */
    AdDto mapAdToAdDto(Ad ad);

    /**
     * Метод, преобразующий объект класса AdDto в объект класса Ad.
     *
     * @param adDto
     * @return Ad
     */
    Ad mapAdsDtoToAd(AdDto adDto);

    /**
     * Метод, преобразующий объект класса Ad в объект класса FullAdDto.
     *
     * @param ad
     * @return FullAdDto
     */
    FullAdDto mapAdToFullAdsDTo(Ad ad);

    /**
     * Метод, преобразующий объект класса CreateAdDto в объект класса Ad.
     *
     * @param createAdDto
     * @return Ad
     */
    Ad mapCreatedAdsDtoToAd(CreateAdDto createAdDto);

    /**
     * Метод, преобразующий объект класса Collection<Ad> в объект класса Collection<AdDto>.
     *
     * @param adCollection
     * @return Collection<AdDto>
     */
    Collection<AdDto> mapAdListToAdDtoList(Collection<Ad> adCollection);

}

