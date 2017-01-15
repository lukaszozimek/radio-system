package io.protone.service.mapper;

import io.protone.domain.TRACampaign;

import io.protone.domain.TRACustomer;

import io.protone.service.dto.TRACampaignDTO;

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

public class TRACampaignMapperImpl implements TRACampaignMapper {

    @Override

    public TRACampaignDTO tRACampaignToTRACampaignDTO(TRACampaign tRACampaign) {

        if ( tRACampaign == null ) {

            return null;
        }

        TRACampaignDTO tRACampaignDTO = new TRACampaignDTO();

        tRACampaignDTO.setCustomerId( tRACampaignCustomerId( tRACampaign ) );

        tRACampaignDTO.setId( tRACampaign.getId() );

        tRACampaignDTO.setName( tRACampaign.getName() );

        tRACampaignDTO.setStartDate( tRACampaign.getStartDate() );

        tRACampaignDTO.setEndDate( tRACampaign.getEndDate() );

        tRACampaignDTO.setPrize( tRACampaign.getPrize() );

        return tRACampaignDTO;
    }

    @Override

    public List<TRACampaignDTO> tRACampaignsToTRACampaignDTOs(List<TRACampaign> tRACampaigns) {

        if ( tRACampaigns == null ) {

            return null;
        }

        List<TRACampaignDTO> list = new ArrayList<TRACampaignDTO>();

        for ( TRACampaign tRACampaign : tRACampaigns ) {

            list.add( tRACampaignToTRACampaignDTO( tRACampaign ) );
        }

        return list;
    }

    @Override

    public TRACampaign tRACampaignDTOToTRACampaign(TRACampaignDTO tRACampaignDTO) {

        if ( tRACampaignDTO == null ) {

            return null;
        }

        TRACampaign tRACampaign = new TRACampaign();

        tRACampaign.setCustomer( tRACustomerFromId( tRACampaignDTO.getCustomerId() ) );

        tRACampaign.setId( tRACampaignDTO.getId() );

        tRACampaign.setName( tRACampaignDTO.getName() );

        tRACampaign.setStartDate( tRACampaignDTO.getStartDate() );

        tRACampaign.setEndDate( tRACampaignDTO.getEndDate() );

        tRACampaign.setPrize( tRACampaignDTO.getPrize() );

        return tRACampaign;
    }

    @Override

    public List<TRACampaign> tRACampaignDTOsToTRACampaigns(List<TRACampaignDTO> tRACampaignDTOs) {

        if ( tRACampaignDTOs == null ) {

            return null;
        }

        List<TRACampaign> list = new ArrayList<TRACampaign>();

        for ( TRACampaignDTO tRACampaignDTO : tRACampaignDTOs ) {

            list.add( tRACampaignDTOToTRACampaign( tRACampaignDTO ) );
        }

        return list;
    }

    private Long tRACampaignCustomerId(TRACampaign tRACampaign) {

        if ( tRACampaign == null ) {

            return null;
        }

        TRACustomer customer = tRACampaign.getCustomer();

        if ( customer == null ) {

            return null;
        }

        Long id = customer.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

