package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CORPropertyKey;

import io.protone.service.dto.CORPropertyKeyDTO;

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

public class CORPropertyKeyMapperImpl implements CORPropertyKeyMapper {

    @Override

    public CORPropertyKeyDTO cORPropertyKeyToCORPropertyKeyDTO(CORPropertyKey cORPropertyKey) {

        if ( cORPropertyKey == null ) {

            return null;
        }

        CORPropertyKeyDTO cORPropertyKeyDTO = new CORPropertyKeyDTO();

        cORPropertyKeyDTO.setNetworkId( cORPropertyKeyNetworkId( cORPropertyKey ) );

        cORPropertyKeyDTO.setId( cORPropertyKey.getId() );

        cORPropertyKeyDTO.setKey( cORPropertyKey.getKey() );

        return cORPropertyKeyDTO;
    }

    @Override

    public List<CORPropertyKeyDTO> cORPropertyKeysToCORPropertyKeyDTOs(List<CORPropertyKey> cORPropertyKeys) {

        if ( cORPropertyKeys == null ) {

            return null;
        }

        List<CORPropertyKeyDTO> list = new ArrayList<CORPropertyKeyDTO>();

        for ( CORPropertyKey cORPropertyKey : cORPropertyKeys ) {

            list.add( cORPropertyKeyToCORPropertyKeyDTO( cORPropertyKey ) );
        }

        return list;
    }

    @Override

    public CORPropertyKey cORPropertyKeyDTOToCORPropertyKey(CORPropertyKeyDTO cORPropertyKeyDTO) {

        if ( cORPropertyKeyDTO == null ) {

            return null;
        }

        CORPropertyKey cORPropertyKey = new CORPropertyKey();

        cORPropertyKey.setNetwork( cORNetworkFromId( cORPropertyKeyDTO.getNetworkId() ) );

        cORPropertyKey.setId( cORPropertyKeyDTO.getId() );

        cORPropertyKey.setKey( cORPropertyKeyDTO.getKey() );

        return cORPropertyKey;
    }

    @Override

    public List<CORPropertyKey> cORPropertyKeyDTOsToCORPropertyKeys(List<CORPropertyKeyDTO> cORPropertyKeyDTOs) {

        if ( cORPropertyKeyDTOs == null ) {

            return null;
        }

        List<CORPropertyKey> list = new ArrayList<CORPropertyKey>();

        for ( CORPropertyKeyDTO cORPropertyKeyDTO : cORPropertyKeyDTOs ) {

            list.add( cORPropertyKeyDTOToCORPropertyKey( cORPropertyKeyDTO ) );
        }

        return list;
    }

    private Long cORPropertyKeyNetworkId(CORPropertyKey cORPropertyKey) {

        if ( cORPropertyKey == null ) {

            return null;
        }

        CORNetwork network = cORPropertyKey.getNetwork();

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

