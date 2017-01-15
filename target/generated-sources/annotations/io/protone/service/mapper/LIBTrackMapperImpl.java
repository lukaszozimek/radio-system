package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.LIBAlbum;

import io.protone.domain.LIBArtist;

import io.protone.domain.LIBTrack;

import io.protone.service.dto.LIBTrackDTO;

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

public class LIBTrackMapperImpl implements LIBTrackMapper {

    @Override

    public LIBTrackDTO lIBTrackToLIBTrackDTO(LIBTrack lIBTrack) {

        if ( lIBTrack == null ) {

            return null;
        }

        LIBTrackDTO lIBTrackDTO = new LIBTrackDTO();

        lIBTrackDTO.setAlbumId( lIBTrackAlbumId( lIBTrack ) );

        lIBTrackDTO.setArtistId( lIBTrackArtistId( lIBTrack ) );

        lIBTrackDTO.setNetworkId( lIBTrackNetworkId( lIBTrack ) );

        lIBTrackDTO.setId( lIBTrack.getId() );

        lIBTrackDTO.setDiscNo( lIBTrack.getDiscNo() );

        lIBTrackDTO.setTrackNo( lIBTrack.getTrackNo() );

        lIBTrackDTO.setName( lIBTrack.getName() );

        lIBTrackDTO.setLength( lIBTrack.getLength() );

        lIBTrackDTO.setDescription( lIBTrack.getDescription() );

        return lIBTrackDTO;
    }

    @Override

    public List<LIBTrackDTO> lIBTracksToLIBTrackDTOs(List<LIBTrack> lIBTracks) {

        if ( lIBTracks == null ) {

            return null;
        }

        List<LIBTrackDTO> list = new ArrayList<LIBTrackDTO>();

        for ( LIBTrack lIBTrack : lIBTracks ) {

            list.add( lIBTrackToLIBTrackDTO( lIBTrack ) );
        }

        return list;
    }

    @Override

    public LIBTrack lIBTrackDTOToLIBTrack(LIBTrackDTO lIBTrackDTO) {

        if ( lIBTrackDTO == null ) {

            return null;
        }

        LIBTrack lIBTrack = new LIBTrack();

        lIBTrack.setArtist( lIBArtistFromId( lIBTrackDTO.getArtistId() ) );

        lIBTrack.setAlbum( lIBAlbumFromId( lIBTrackDTO.getAlbumId() ) );

        lIBTrack.setNetwork( cORNetworkFromId( lIBTrackDTO.getNetworkId() ) );

        lIBTrack.setId( lIBTrackDTO.getId() );

        lIBTrack.setDiscNo( lIBTrackDTO.getDiscNo() );

        lIBTrack.setTrackNo( lIBTrackDTO.getTrackNo() );

        lIBTrack.setName( lIBTrackDTO.getName() );

        lIBTrack.setLength( lIBTrackDTO.getLength() );

        lIBTrack.setDescription( lIBTrackDTO.getDescription() );

        return lIBTrack;
    }

    @Override

    public List<LIBTrack> lIBTrackDTOsToLIBTracks(List<LIBTrackDTO> lIBTrackDTOs) {

        if ( lIBTrackDTOs == null ) {

            return null;
        }

        List<LIBTrack> list = new ArrayList<LIBTrack>();

        for ( LIBTrackDTO lIBTrackDTO : lIBTrackDTOs ) {

            list.add( lIBTrackDTOToLIBTrack( lIBTrackDTO ) );
        }

        return list;
    }

    private Long lIBTrackAlbumId(LIBTrack lIBTrack) {

        if ( lIBTrack == null ) {

            return null;
        }

        LIBAlbum album = lIBTrack.getAlbum();

        if ( album == null ) {

            return null;
        }

        Long id = album.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBTrackArtistId(LIBTrack lIBTrack) {

        if ( lIBTrack == null ) {

            return null;
        }

        LIBArtist artist = lIBTrack.getArtist();

        if ( artist == null ) {

            return null;
        }

        Long id = artist.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBTrackNetworkId(LIBTrack lIBTrack) {

        if ( lIBTrack == null ) {

            return null;
        }

        CORNetwork network = lIBTrack.getNetwork();

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

