package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.TRAPrice;

import io.protone.service.dto.TRAPriceDTO;

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

public class TRAPriceMapperImpl implements TRAPriceMapper {

    @Override

    public TRAPriceDTO tRAPriceToTRAPriceDTO(TRAPrice tRAPrice) {

        if ( tRAPrice == null ) {

            return null;
        }

        TRAPriceDTO tRAPriceDTO = new TRAPriceDTO();

        tRAPriceDTO.setNetworkId( tRAPriceNetworkId( tRAPrice ) );

        tRAPriceDTO.setId( tRAPrice.getId() );

        tRAPriceDTO.setName( tRAPrice.getName() );

        tRAPriceDTO.setValidFrom( tRAPrice.getValidFrom() );

        tRAPriceDTO.setValidTo( tRAPrice.getValidTo() );

        tRAPriceDTO.setPrice( tRAPrice.getPrice() );

        tRAPriceDTO.setBaseLength( tRAPrice.getBaseLength() );

        tRAPriceDTO.setPriceAlternative( tRAPrice.getPriceAlternative() );

        return tRAPriceDTO;
    }

    @Override

    public List<TRAPriceDTO> tRAPricesToTRAPriceDTOs(List<TRAPrice> tRAPrices) {

        if ( tRAPrices == null ) {

            return null;
        }

        List<TRAPriceDTO> list = new ArrayList<TRAPriceDTO>();

        for ( TRAPrice tRAPrice : tRAPrices ) {

            list.add( tRAPriceToTRAPriceDTO( tRAPrice ) );
        }

        return list;
    }

    @Override

    public TRAPrice tRAPriceDTOToTRAPrice(TRAPriceDTO tRAPriceDTO) {

        if ( tRAPriceDTO == null ) {

            return null;
        }

        TRAPrice tRAPrice = new TRAPrice();

        tRAPrice.setNetwork( cORNetworkFromId( tRAPriceDTO.getNetworkId() ) );

        tRAPrice.setId( tRAPriceDTO.getId() );

        tRAPrice.setName( tRAPriceDTO.getName() );

        tRAPrice.setValidFrom( tRAPriceDTO.getValidFrom() );

        tRAPrice.setValidTo( tRAPriceDTO.getValidTo() );

        tRAPrice.setPrice( tRAPriceDTO.getPrice() );

        tRAPrice.setBaseLength( tRAPriceDTO.getBaseLength() );

        tRAPrice.setPriceAlternative( tRAPriceDTO.getPriceAlternative() );

        return tRAPrice;
    }

    @Override

    public List<TRAPrice> tRAPriceDTOsToTRAPrices(List<TRAPriceDTO> tRAPriceDTOs) {

        if ( tRAPriceDTOs == null ) {

            return null;
        }

        List<TRAPrice> list = new ArrayList<TRAPrice>();

        for ( TRAPriceDTO tRAPriceDTO : tRAPriceDTOs ) {

            list.add( tRAPriceDTOToTRAPrice( tRAPriceDTO ) );
        }

        return list;
    }

    private Long tRAPriceNetworkId(TRAPrice tRAPrice) {

        if ( tRAPrice == null ) {

            return null;
        }

        CORNetwork network = tRAPrice.getNetwork();

        if ( network == null ) {

            return null;
        }

        Long id = network.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

