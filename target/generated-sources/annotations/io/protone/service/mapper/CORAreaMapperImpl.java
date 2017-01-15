package io.protone.service.mapper;

import io.protone.domain.CORArea;

import io.protone.domain.CORNetwork;

import io.protone.service.dto.CORAreaDTO;

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

public class CORAreaMapperImpl implements CORAreaMapper {

    @Override

    public CORAreaDTO cORAreaToCORAreaDTO(CORArea cORArea) {

        if ( cORArea == null ) {

            return null;
        }

        CORAreaDTO cORAreaDTO = new CORAreaDTO();

        cORAreaDTO.setNetworkId( cORAreaNetworkId( cORArea ) );

        cORAreaDTO.setId( cORArea.getId() );

        cORAreaDTO.setName( cORArea.getName() );

        return cORAreaDTO;
    }

    @Override

    public List<CORAreaDTO> cORAreasToCORAreaDTOs(List<CORArea> cORAreas) {

        if ( cORAreas == null ) {

            return null;
        }

        List<CORAreaDTO> list = new ArrayList<CORAreaDTO>();

        for ( CORArea cORArea : cORAreas ) {

            list.add( cORAreaToCORAreaDTO( cORArea ) );
        }

        return list;
    }

    @Override

    public CORArea cORAreaDTOToCORArea(CORAreaDTO cORAreaDTO) {

        if ( cORAreaDTO == null ) {

            return null;
        }

        CORArea cORArea = new CORArea();

        cORArea.setNetwork( cORNetworkFromId( cORAreaDTO.getNetworkId() ) );

        cORArea.setId( cORAreaDTO.getId() );

        cORArea.setName( cORAreaDTO.getName() );

        return cORArea;
    }

    @Override

    public List<CORArea> cORAreaDTOsToCORAreas(List<CORAreaDTO> cORAreaDTOs) {

        if ( cORAreaDTOs == null ) {

            return null;
        }

        List<CORArea> list = new ArrayList<CORArea>();

        for ( CORAreaDTO cORAreaDTO : cORAreaDTOs ) {

            list.add( cORAreaDTOToCORArea( cORAreaDTO ) );
        }

        return list;
    }

    private Long cORAreaNetworkId(CORArea cORArea) {

        if ( cORArea == null ) {

            return null;
        }

        CORNetwork network = cORArea.getNetwork();

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

