package io.protone.service.mapper;

import io.protone.domain.LIBAlbum;

import io.protone.domain.LIBArtist;

import io.protone.domain.LIBLabel;

import io.protone.domain.LIBLibrary;

import io.protone.domain.LIBMediaItem;

import io.protone.domain.LIBTrack;

import io.protone.service.dto.LIBMediaItemDTO;

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

public class LIBMediaItemMapperImpl implements LIBMediaItemMapper {

    @Override

    public LIBMediaItemDTO lIBMediaItemToLIBMediaItemDTO(LIBMediaItem lIBMediaItem) {

        if ( lIBMediaItem == null ) {

            return null;
        }

        LIBMediaItemDTO lIBMediaItemDTO = new LIBMediaItemDTO();

        lIBMediaItemDTO.setAlbumId( lIBMediaItemAlbumId( lIBMediaItem ) );

        lIBMediaItemDTO.setArtistId( lIBMediaItemArtistId( lIBMediaItem ) );

        lIBMediaItemDTO.setLabelId( lIBMediaItemLabelId( lIBMediaItem ) );

        lIBMediaItemDTO.setTrackId( lIBMediaItemTrackId( lIBMediaItem ) );

        lIBMediaItemDTO.setLibraryId( lIBMediaItemLibraryId( lIBMediaItem ) );

        lIBMediaItemDTO.setId( lIBMediaItem.getId() );

        lIBMediaItemDTO.setIdx( lIBMediaItem.getIdx() );

        lIBMediaItemDTO.setName( lIBMediaItem.getName() );

        lIBMediaItemDTO.setItemType( lIBMediaItem.getItemType() );

        lIBMediaItemDTO.setLength( lIBMediaItem.getLength() );

        lIBMediaItemDTO.setState( lIBMediaItem.getState() );

        lIBMediaItemDTO.setCommand( lIBMediaItem.getCommand() );

        lIBMediaItemDTO.setDescription( lIBMediaItem.getDescription() );

        return lIBMediaItemDTO;
    }

    @Override

    public List<LIBMediaItemDTO> lIBMediaItemsToLIBMediaItemDTOs(List<LIBMediaItem> lIBMediaItems) {

        if ( lIBMediaItems == null ) {

            return null;
        }

        List<LIBMediaItemDTO> list = new ArrayList<LIBMediaItemDTO>();

        for ( LIBMediaItem lIBMediaItem : lIBMediaItems ) {

            list.add( lIBMediaItemToLIBMediaItemDTO( lIBMediaItem ) );
        }

        return list;
    }

    @Override

    public LIBMediaItem lIBMediaItemDTOToLIBMediaItem(LIBMediaItemDTO lIBMediaItemDTO) {

        if ( lIBMediaItemDTO == null ) {

            return null;
        }

        LIBMediaItem lIBMediaItem = new LIBMediaItem();

        lIBMediaItem.setLibrary( lIBLibraryFromId( lIBMediaItemDTO.getLibraryId() ) );

        lIBMediaItem.setLabel( lIBLabelFromId( lIBMediaItemDTO.getLabelId() ) );

        lIBMediaItem.setTrack( lIBTrackFromId( lIBMediaItemDTO.getTrackId() ) );

        lIBMediaItem.setArtist( lIBArtistFromId( lIBMediaItemDTO.getArtistId() ) );

        lIBMediaItem.setAlbum( lIBAlbumFromId( lIBMediaItemDTO.getAlbumId() ) );

        lIBMediaItem.setId( lIBMediaItemDTO.getId() );

        lIBMediaItem.setIdx( lIBMediaItemDTO.getIdx() );

        lIBMediaItem.setName( lIBMediaItemDTO.getName() );

        lIBMediaItem.setItemType( lIBMediaItemDTO.getItemType() );

        lIBMediaItem.setLength( lIBMediaItemDTO.getLength() );

        lIBMediaItem.setState( lIBMediaItemDTO.getState() );

        lIBMediaItem.setCommand( lIBMediaItemDTO.getCommand() );

        lIBMediaItem.setDescription( lIBMediaItemDTO.getDescription() );

        return lIBMediaItem;
    }

    @Override

    public List<LIBMediaItem> lIBMediaItemDTOsToLIBMediaItems(List<LIBMediaItemDTO> lIBMediaItemDTOs) {

        if ( lIBMediaItemDTOs == null ) {

            return null;
        }

        List<LIBMediaItem> list = new ArrayList<LIBMediaItem>();

        for ( LIBMediaItemDTO lIBMediaItemDTO : lIBMediaItemDTOs ) {

            list.add( lIBMediaItemDTOToLIBMediaItem( lIBMediaItemDTO ) );
        }

        return list;
    }

    private Long lIBMediaItemAlbumId(LIBMediaItem lIBMediaItem) {

        if ( lIBMediaItem == null ) {

            return null;
        }

        LIBAlbum album = lIBMediaItem.getAlbum();

        if ( album == null ) {

            return null;
        }

        Long id = album.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBMediaItemArtistId(LIBMediaItem lIBMediaItem) {

        if ( lIBMediaItem == null ) {

            return null;
        }

        LIBArtist artist = lIBMediaItem.getArtist();

        if ( artist == null ) {

            return null;
        }

        Long id = artist.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBMediaItemLabelId(LIBMediaItem lIBMediaItem) {

        if ( lIBMediaItem == null ) {

            return null;
        }

        LIBLabel label = lIBMediaItem.getLabel();

        if ( label == null ) {

            return null;
        }

        Long id = label.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBMediaItemTrackId(LIBMediaItem lIBMediaItem) {

        if ( lIBMediaItem == null ) {

            return null;
        }

        LIBTrack track = lIBMediaItem.getTrack();

        if ( track == null ) {

            return null;
        }

        Long id = track.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBMediaItemLibraryId(LIBMediaItem lIBMediaItem) {

        if ( lIBMediaItem == null ) {

            return null;
        }

        LIBLibrary library = lIBMediaItem.getLibrary();

        if ( library == null ) {

            return null;
        }

        Long id = library.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

