package io.protone.service.mapper;

import io.protone.domain.LIBMediaItem;

import io.protone.domain.TRAAdvertisement;

import io.protone.domain.TRACustomer;

import io.protone.domain.TRAIndustry;

import io.protone.service.dto.TRAAdvertisementDTO;

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

public class TRAAdvertisementMapperImpl implements TRAAdvertisementMapper {

    @Override

    public TRAAdvertisementDTO tRAAdvertisementToTRAAdvertisementDTO(TRAAdvertisement tRAAdvertisement) {

        if ( tRAAdvertisement == null ) {

            return null;
        }

        TRAAdvertisementDTO tRAAdvertisementDTO = new TRAAdvertisementDTO();

        tRAAdvertisementDTO.setCustomerId( tRAAdvertisementCustomerId( tRAAdvertisement ) );

        tRAAdvertisementDTO.setMediaItemId( tRAAdvertisementMediaItemId( tRAAdvertisement ) );

        tRAAdvertisementDTO.setIndustryId( tRAAdvertisementIndustryId( tRAAdvertisement ) );

        tRAAdvertisementDTO.setId( tRAAdvertisement.getId() );

        tRAAdvertisementDTO.setName( tRAAdvertisement.getName() );

        tRAAdvertisementDTO.setDescription( tRAAdvertisement.getDescription() );

        return tRAAdvertisementDTO;
    }

    @Override

    public List<TRAAdvertisementDTO> tRAAdvertisementsToTRAAdvertisementDTOs(List<TRAAdvertisement> tRAAdvertisements) {

        if ( tRAAdvertisements == null ) {

            return null;
        }

        List<TRAAdvertisementDTO> list = new ArrayList<TRAAdvertisementDTO>();

        for ( TRAAdvertisement tRAAdvertisement : tRAAdvertisements ) {

            list.add( tRAAdvertisementToTRAAdvertisementDTO( tRAAdvertisement ) );
        }

        return list;
    }

    @Override

    public TRAAdvertisement tRAAdvertisementDTOToTRAAdvertisement(TRAAdvertisementDTO tRAAdvertisementDTO) {

        if ( tRAAdvertisementDTO == null ) {

            return null;
        }

        TRAAdvertisement tRAAdvertisement = new TRAAdvertisement();

        tRAAdvertisement.setMediaItem( lIBMediaItemFromId( tRAAdvertisementDTO.getMediaItemId() ) );

        tRAAdvertisement.setIndustry( tRAIndustryFromId( tRAAdvertisementDTO.getIndustryId() ) );

        tRAAdvertisement.setCustomer( tRACustomerFromId( tRAAdvertisementDTO.getCustomerId() ) );

        tRAAdvertisement.setId( tRAAdvertisementDTO.getId() );

        tRAAdvertisement.setName( tRAAdvertisementDTO.getName() );

        tRAAdvertisement.setDescription( tRAAdvertisementDTO.getDescription() );

        return tRAAdvertisement;
    }

    @Override

    public List<TRAAdvertisement> tRAAdvertisementDTOsToTRAAdvertisements(List<TRAAdvertisementDTO> tRAAdvertisementDTOs) {

        if ( tRAAdvertisementDTOs == null ) {

            return null;
        }

        List<TRAAdvertisement> list = new ArrayList<TRAAdvertisement>();

        for ( TRAAdvertisementDTO tRAAdvertisementDTO : tRAAdvertisementDTOs ) {

            list.add( tRAAdvertisementDTOToTRAAdvertisement( tRAAdvertisementDTO ) );
        }

        return list;
    }

    private Long tRAAdvertisementCustomerId(TRAAdvertisement tRAAdvertisement) {

        if ( tRAAdvertisement == null ) {

            return null;
        }

        TRACustomer customer = tRAAdvertisement.getCustomer();

        if ( customer == null ) {

            return null;
        }

        Long id = customer.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long tRAAdvertisementMediaItemId(TRAAdvertisement tRAAdvertisement) {

        if ( tRAAdvertisement == null ) {

            return null;
        }

        LIBMediaItem mediaItem = tRAAdvertisement.getMediaItem();

        if ( mediaItem == null ) {

            return null;
        }

        Long id = mediaItem.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long tRAAdvertisementIndustryId(TRAAdvertisement tRAAdvertisement) {

        if ( tRAAdvertisement == null ) {

            return null;
        }

        TRAIndustry industry = tRAAdvertisement.getIndustry();

        if ( industry == null ) {

            return null;
        }

        Long id = industry.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

