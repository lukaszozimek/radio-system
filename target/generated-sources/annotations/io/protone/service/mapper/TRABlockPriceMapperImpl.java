package io.protone.service.mapper;

import io.protone.domain.TRABlockPrice;

import io.protone.service.dto.TRABlockPriceDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-01-15T01:15:41+0100",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_71 (Oracle Corporation)"

)

@Component

public class TRABlockPriceMapperImpl implements TRABlockPriceMapper {

    @Override

    public TRABlockPriceDTO tRABlockPriceToTRABlockPriceDTO(TRABlockPrice tRABlockPrice) {

        if ( tRABlockPrice == null ) {

            return null;
        }

        TRABlockPriceDTO tRABlockPriceDTO = new TRABlockPriceDTO();

        tRABlockPriceDTO.setId( tRABlockPrice.getId() );

        tRABlockPriceDTO.setCalculatedPrice( tRABlockPrice.getCalculatedPrice() );

        return tRABlockPriceDTO;
    }

    @Override

    public List<TRABlockPriceDTO> tRABlockPricesToTRABlockPriceDTOs(List<TRABlockPrice> tRABlockPrices) {

        if ( tRABlockPrices == null ) {

            return null;
        }

        List<TRABlockPriceDTO> list = new ArrayList<TRABlockPriceDTO>();

        for ( TRABlockPrice tRABlockPrice : tRABlockPrices ) {

            list.add( tRABlockPriceToTRABlockPriceDTO( tRABlockPrice ) );
        }

        return list;
    }

    @Override

    public TRABlockPrice tRABlockPriceDTOToTRABlockPrice(TRABlockPriceDTO tRABlockPriceDTO) {

        if ( tRABlockPriceDTO == null ) {

            return null;
        }

        TRABlockPrice tRABlockPrice = new TRABlockPrice();

        tRABlockPrice.setId( tRABlockPriceDTO.getId() );

        tRABlockPrice.setCalculatedPrice( tRABlockPriceDTO.getCalculatedPrice() );

        return tRABlockPrice;
    }

    @Override

    public List<TRABlockPrice> tRABlockPriceDTOsToTRABlockPrices(List<TRABlockPriceDTO> tRABlockPriceDTOs) {

        if ( tRABlockPriceDTOs == null ) {

            return null;
        }

        List<TRABlockPrice> list = new ArrayList<TRABlockPrice>();

        for ( TRABlockPriceDTO tRABlockPriceDTO : tRABlockPriceDTOs ) {

            list.add( tRABlockPriceDTOToTRABlockPrice( tRABlockPriceDTO ) );
        }

        return list;
    }
}

