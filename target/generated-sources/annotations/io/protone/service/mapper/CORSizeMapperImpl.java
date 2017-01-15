package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CORSize;

import io.protone.service.dto.CORSizeDTO;

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

public class CORSizeMapperImpl implements CORSizeMapper {

    @Override

    public CORSizeDTO cORSizeToCORSizeDTO(CORSize cORSize) {

        if ( cORSize == null ) {

            return null;
        }

        CORSizeDTO cORSizeDTO = new CORSizeDTO();

        cORSizeDTO.setNetworkId( cORSizeNetworkId( cORSize ) );

        cORSizeDTO.setId( cORSize.getId() );

        cORSizeDTO.setName( cORSize.getName() );

        return cORSizeDTO;
    }

    @Override

    public List<CORSizeDTO> cORSizesToCORSizeDTOs(List<CORSize> cORSizes) {

        if ( cORSizes == null ) {

            return null;
        }

        List<CORSizeDTO> list = new ArrayList<CORSizeDTO>();

        for ( CORSize cORSize : cORSizes ) {

            list.add( cORSizeToCORSizeDTO( cORSize ) );
        }

        return list;
    }

    @Override

    public CORSize cORSizeDTOToCORSize(CORSizeDTO cORSizeDTO) {

        if ( cORSizeDTO == null ) {

            return null;
        }

        CORSize cORSize = new CORSize();

        cORSize.setNetwork( cORNetworkFromId( cORSizeDTO.getNetworkId() ) );

        cORSize.setId( cORSizeDTO.getId() );

        cORSize.setName( cORSizeDTO.getName() );

        return cORSize;
    }

    @Override

    public List<CORSize> cORSizeDTOsToCORSizes(List<CORSizeDTO> cORSizeDTOs) {

        if ( cORSizeDTOs == null ) {

            return null;
        }

        List<CORSize> list = new ArrayList<CORSize>();

        for ( CORSizeDTO cORSizeDTO : cORSizeDTOs ) {

            list.add( cORSizeDTOToCORSize( cORSizeDTO ) );
        }

        return list;
    }

    private Long cORSizeNetworkId(CORSize cORSize) {

        if ( cORSize == null ) {

            return null;
        }

        CORNetwork network = cORSize.getNetwork();

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

