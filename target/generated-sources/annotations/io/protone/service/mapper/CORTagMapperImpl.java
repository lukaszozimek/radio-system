package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CORTag;

import io.protone.service.dto.CORTagDTO;

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

public class CORTagMapperImpl implements CORTagMapper {

    @Override

    public CORTagDTO cORTagToCORTagDTO(CORTag cORTag) {

        if ( cORTag == null ) {

            return null;
        }

        CORTagDTO cORTagDTO = new CORTagDTO();

        cORTagDTO.setNetworkId( cORTagNetworkId( cORTag ) );

        cORTagDTO.setId( cORTag.getId() );

        cORTagDTO.setTag( cORTag.getTag() );

        return cORTagDTO;
    }

    @Override

    public List<CORTagDTO> cORTagsToCORTagDTOs(List<CORTag> cORTags) {

        if ( cORTags == null ) {

            return null;
        }

        List<CORTagDTO> list = new ArrayList<CORTagDTO>();

        for ( CORTag cORTag : cORTags ) {

            list.add( cORTagToCORTagDTO( cORTag ) );
        }

        return list;
    }

    @Override

    public CORTag cORTagDTOToCORTag(CORTagDTO cORTagDTO) {

        if ( cORTagDTO == null ) {

            return null;
        }

        CORTag cORTag = new CORTag();

        cORTag.setNetwork( cORNetworkFromId( cORTagDTO.getNetworkId() ) );

        cORTag.setId( cORTagDTO.getId() );

        cORTag.setTag( cORTagDTO.getTag() );

        return cORTag;
    }

    @Override

    public List<CORTag> cORTagDTOsToCORTags(List<CORTagDTO> cORTagDTOs) {

        if ( cORTagDTOs == null ) {

            return null;
        }

        List<CORTag> list = new ArrayList<CORTag>();

        for ( CORTagDTO cORTagDTO : cORTagDTOs ) {

            list.add( cORTagDTOToCORTag( cORTagDTO ) );
        }

        return list;
    }

    private Long cORTagNetworkId(CORTag cORTag) {

        if ( cORTag == null ) {

            return null;
        }

        CORNetwork network = cORTag.getNetwork();

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

