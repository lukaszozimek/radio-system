package io.protone.service.mapper;

import io.protone.domain.LIBMarker;

import io.protone.domain.LIBMediaItem;

import io.protone.service.dto.LIBMarkerDTO;

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

public class LIBMarkerMapperImpl implements LIBMarkerMapper {

    @Override

    public LIBMarkerDTO lIBMarkerToLIBMarkerDTO(LIBMarker lIBMarker) {

        if ( lIBMarker == null ) {

            return null;
        }

        LIBMarkerDTO lIBMarkerDTO = new LIBMarkerDTO();

        lIBMarkerDTO.setMediaItemId( lIBMarkerMediaItemId( lIBMarker ) );

        lIBMarkerDTO.setId( lIBMarker.getId() );

        lIBMarkerDTO.setMarkerType( lIBMarker.getMarkerType() );

        lIBMarkerDTO.setName( lIBMarker.getName() );

        lIBMarkerDTO.setStartTime( lIBMarker.getStartTime() );

        return lIBMarkerDTO;
    }

    @Override

    public List<LIBMarkerDTO> lIBMarkersToLIBMarkerDTOs(List<LIBMarker> lIBMarkers) {

        if ( lIBMarkers == null ) {

            return null;
        }

        List<LIBMarkerDTO> list = new ArrayList<LIBMarkerDTO>();

        for ( LIBMarker lIBMarker : lIBMarkers ) {

            list.add( lIBMarkerToLIBMarkerDTO( lIBMarker ) );
        }

        return list;
    }

    @Override

    public LIBMarker lIBMarkerDTOToLIBMarker(LIBMarkerDTO lIBMarkerDTO) {

        if ( lIBMarkerDTO == null ) {

            return null;
        }

        LIBMarker lIBMarker = new LIBMarker();

        lIBMarker.setMediaItem( lIBMediaItemFromId( lIBMarkerDTO.getMediaItemId() ) );

        lIBMarker.setId( lIBMarkerDTO.getId() );

        lIBMarker.setMarkerType( lIBMarkerDTO.getMarkerType() );

        lIBMarker.setName( lIBMarkerDTO.getName() );

        lIBMarker.setStartTime( lIBMarkerDTO.getStartTime() );

        return lIBMarker;
    }

    @Override

    public List<LIBMarker> lIBMarkerDTOsToLIBMarkers(List<LIBMarkerDTO> lIBMarkerDTOs) {

        if ( lIBMarkerDTOs == null ) {

            return null;
        }

        List<LIBMarker> list = new ArrayList<LIBMarker>();

        for ( LIBMarkerDTO lIBMarkerDTO : lIBMarkerDTOs ) {

            list.add( lIBMarkerDTOToLIBMarker( lIBMarkerDTO ) );
        }

        return list;
    }

    private Long lIBMarkerMediaItemId(LIBMarker lIBMarker) {

        if ( lIBMarker == null ) {

            return null;
        }

        LIBMediaItem mediaItem = lIBMarker.getMediaItem();

        if ( mediaItem == null ) {

            return null;
        }

        Long id = mediaItem.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

