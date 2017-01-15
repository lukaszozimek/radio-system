package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.TRAIndustry;

import io.protone.service.dto.TRAIndustryDTO;

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

public class TRAIndustryMapperImpl implements TRAIndustryMapper {

    @Override

    public TRAIndustryDTO tRAIndustryToTRAIndustryDTO(TRAIndustry tRAIndustry) {

        if ( tRAIndustry == null ) {

            return null;
        }

        TRAIndustryDTO tRAIndustryDTO = new TRAIndustryDTO();

        tRAIndustryDTO.setNetworkId( tRAIndustryNetworkId( tRAIndustry ) );

        tRAIndustryDTO.setId( tRAIndustry.getId() );

        tRAIndustryDTO.setName( tRAIndustry.getName() );

        return tRAIndustryDTO;
    }

    @Override

    public List<TRAIndustryDTO> tRAIndustriesToTRAIndustryDTOs(List<TRAIndustry> tRAIndustries) {

        if ( tRAIndustries == null ) {

            return null;
        }

        List<TRAIndustryDTO> list = new ArrayList<TRAIndustryDTO>();

        for ( TRAIndustry tRAIndustry : tRAIndustries ) {

            list.add( tRAIndustryToTRAIndustryDTO( tRAIndustry ) );
        }

        return list;
    }

    @Override

    public TRAIndustry tRAIndustryDTOToTRAIndustry(TRAIndustryDTO tRAIndustryDTO) {

        if ( tRAIndustryDTO == null ) {

            return null;
        }

        TRAIndustry tRAIndustry = new TRAIndustry();

        tRAIndustry.setNetwork( cORNetworkFromId( tRAIndustryDTO.getNetworkId() ) );

        tRAIndustry.setId( tRAIndustryDTO.getId() );

        tRAIndustry.setName( tRAIndustryDTO.getName() );

        return tRAIndustry;
    }

    @Override

    public List<TRAIndustry> tRAIndustryDTOsToTRAIndustries(List<TRAIndustryDTO> tRAIndustryDTOs) {

        if ( tRAIndustryDTOs == null ) {

            return null;
        }

        List<TRAIndustry> list = new ArrayList<TRAIndustry>();

        for ( TRAIndustryDTO tRAIndustryDTO : tRAIndustryDTOs ) {

            list.add( tRAIndustryDTOToTRAIndustry( tRAIndustryDTO ) );
        }

        return list;
    }

    private Long tRAIndustryNetworkId(TRAIndustry tRAIndustry) {

        if ( tRAIndustry == null ) {

            return null;
        }

        CORNetwork network = tRAIndustry.getNetwork();

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

