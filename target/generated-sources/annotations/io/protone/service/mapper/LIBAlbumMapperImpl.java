package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.LIBAlbum;

import io.protone.domain.LIBArtist;

import io.protone.domain.LIBImageItem;

import io.protone.domain.LIBLabel;

import io.protone.service.dto.LIBAlbumDTO;

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

public class LIBAlbumMapperImpl implements LIBAlbumMapper {

    @Override

    public LIBAlbumDTO lIBAlbumToLIBAlbumDTO(LIBAlbum lIBAlbum) {

        if ( lIBAlbum == null ) {

            return null;
        }

        LIBAlbumDTO lIBAlbumDTO = new LIBAlbumDTO();

        lIBAlbumDTO.setArtistId( lIBAlbumArtistId( lIBAlbum ) );

        lIBAlbumDTO.setNetworkId( lIBAlbumNetworkId( lIBAlbum ) );

        lIBAlbumDTO.setCoverId( lIBAlbumCoverId( lIBAlbum ) );

        lIBAlbumDTO.setLabelId( lIBAlbumLabelId( lIBAlbum ) );

        lIBAlbumDTO.setId( lIBAlbum.getId() );

        lIBAlbumDTO.setAlbumType( lIBAlbum.getAlbumType() );

        lIBAlbumDTO.setName( lIBAlbum.getName() );

        lIBAlbumDTO.setReleaseDate( lIBAlbum.getReleaseDate() );

        lIBAlbumDTO.setDescription( lIBAlbum.getDescription() );

        return lIBAlbumDTO;
    }

    @Override

    public List<LIBAlbumDTO> lIBAlbumsToLIBAlbumDTOs(List<LIBAlbum> lIBAlbums) {

        if ( lIBAlbums == null ) {

            return null;
        }

        List<LIBAlbumDTO> list = new ArrayList<LIBAlbumDTO>();

        for ( LIBAlbum lIBAlbum : lIBAlbums ) {

            list.add( lIBAlbumToLIBAlbumDTO( lIBAlbum ) );
        }

        return list;
    }

    @Override

    public LIBAlbum lIBAlbumDTOToLIBAlbum(LIBAlbumDTO lIBAlbumDTO) {

        if ( lIBAlbumDTO == null ) {

            return null;
        }

        LIBAlbum lIBAlbum = new LIBAlbum();

        lIBAlbum.setCover( lIBImageItemFromId( lIBAlbumDTO.getCoverId() ) );

        lIBAlbum.setLabel( lIBLabelFromId( lIBAlbumDTO.getLabelId() ) );

        lIBAlbum.setArtist( lIBArtistFromId( lIBAlbumDTO.getArtistId() ) );

        lIBAlbum.setNetwork( cORNetworkFromId( lIBAlbumDTO.getNetworkId() ) );

        lIBAlbum.setId( lIBAlbumDTO.getId() );

        lIBAlbum.setAlbumType( lIBAlbumDTO.getAlbumType() );

        lIBAlbum.setName( lIBAlbumDTO.getName() );

        lIBAlbum.setReleaseDate( lIBAlbumDTO.getReleaseDate() );

        lIBAlbum.setDescription( lIBAlbumDTO.getDescription() );

        return lIBAlbum;
    }

    @Override

    public List<LIBAlbum> lIBAlbumDTOsToLIBAlbums(List<LIBAlbumDTO> lIBAlbumDTOs) {

        if ( lIBAlbumDTOs == null ) {

            return null;
        }

        List<LIBAlbum> list = new ArrayList<LIBAlbum>();

        for ( LIBAlbumDTO lIBAlbumDTO : lIBAlbumDTOs ) {

            list.add( lIBAlbumDTOToLIBAlbum( lIBAlbumDTO ) );
        }

        return list;
    }

    private Long lIBAlbumArtistId(LIBAlbum lIBAlbum) {

        if ( lIBAlbum == null ) {

            return null;
        }

        LIBArtist artist = lIBAlbum.getArtist();

        if ( artist == null ) {

            return null;
        }

        Long id = artist.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBAlbumNetworkId(LIBAlbum lIBAlbum) {

        if ( lIBAlbum == null ) {

            return null;
        }

        CORNetwork network = lIBAlbum.getNetwork();

        if ( network == null ) {

            return null;
        }

        Long id = network.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBAlbumCoverId(LIBAlbum lIBAlbum) {

        if ( lIBAlbum == null ) {

            return null;
        }

        LIBImageItem cover = lIBAlbum.getCover();

        if ( cover == null ) {

            return null;
        }

        Long id = cover.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBAlbumLabelId(LIBAlbum lIBAlbum) {

        if ( lIBAlbum == null ) {

            return null;
        }

        LIBLabel label = lIBAlbum.getLabel();

        if ( label == null ) {

            return null;
        }

        Long id = label.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

