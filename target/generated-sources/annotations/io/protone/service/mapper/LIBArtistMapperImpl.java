package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.LIBArtist;

import io.protone.service.dto.LIBArtistDTO;

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

public class LIBArtistMapperImpl implements LIBArtistMapper {

    @Override

    public LIBArtistDTO lIBArtistToLIBArtistDTO(LIBArtist lIBArtist) {

        if ( lIBArtist == null ) {

            return null;
        }

        LIBArtistDTO lIBArtistDTO = new LIBArtistDTO();

        lIBArtistDTO.setNetworkId( lIBArtistNetworkId( lIBArtist ) );

        lIBArtistDTO.setId( lIBArtist.getId() );

        lIBArtistDTO.setName( lIBArtist.getName() );

        lIBArtistDTO.setType( lIBArtist.getType() );

        lIBArtistDTO.setDescription( lIBArtist.getDescription() );

        return lIBArtistDTO;
    }

    @Override

    public List<LIBArtistDTO> lIBArtistsToLIBArtistDTOs(List<LIBArtist> lIBArtists) {

        if ( lIBArtists == null ) {

            return null;
        }

        List<LIBArtistDTO> list = new ArrayList<LIBArtistDTO>();

        for ( LIBArtist lIBArtist : lIBArtists ) {

            list.add( lIBArtistToLIBArtistDTO( lIBArtist ) );
        }

        return list;
    }

    @Override

    public LIBArtist lIBArtistDTOToLIBArtist(LIBArtistDTO lIBArtistDTO) {

        if ( lIBArtistDTO == null ) {

            return null;
        }

        LIBArtist lIBArtist = new LIBArtist();

        lIBArtist.setNetwork( cORNetworkFromId( lIBArtistDTO.getNetworkId() ) );

        lIBArtist.setId( lIBArtistDTO.getId() );

        lIBArtist.setName( lIBArtistDTO.getName() );

        lIBArtist.setType( lIBArtistDTO.getType() );

        lIBArtist.setDescription( lIBArtistDTO.getDescription() );

        return lIBArtist;
    }

    @Override

    public List<LIBArtist> lIBArtistDTOsToLIBArtists(List<LIBArtistDTO> lIBArtistDTOs) {

        if ( lIBArtistDTOs == null ) {

            return null;
        }

        List<LIBArtist> list = new ArrayList<LIBArtist>();

        for ( LIBArtistDTO lIBArtistDTO : lIBArtistDTOs ) {

            list.add( lIBArtistDTOToLIBArtist( lIBArtistDTO ) );
        }

        return list;
    }

    private Long lIBArtistNetworkId(LIBArtist lIBArtist) {

        if ( lIBArtist == null ) {

            return null;
        }

        CORNetwork network = lIBArtist.getNetwork();

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

