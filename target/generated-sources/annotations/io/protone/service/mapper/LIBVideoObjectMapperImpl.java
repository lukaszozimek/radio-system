package io.protone.service.mapper;

import io.protone.domain.LIBCloudObject;

import io.protone.domain.LIBMediaItem;

import io.protone.domain.LIBVideoObject;

import io.protone.service.dto.LIBVideoObjectDTO;

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

public class LIBVideoObjectMapperImpl implements LIBVideoObjectMapper {

    @Override

    public LIBVideoObjectDTO lIBVideoObjectToLIBVideoObjectDTO(LIBVideoObject lIBVideoObject) {

        if ( lIBVideoObject == null ) {

            return null;
        }

        LIBVideoObjectDTO lIBVideoObjectDTO = new LIBVideoObjectDTO();

        lIBVideoObjectDTO.setCloudObjectId( lIBVideoObjectCloudObjectId( lIBVideoObject ) );

        lIBVideoObjectDTO.setMediaItemId( lIBVideoObjectMediaItemId( lIBVideoObject ) );

        lIBVideoObjectDTO.setId( lIBVideoObject.getId() );

        lIBVideoObjectDTO.setWidth( lIBVideoObject.getWidth() );

        lIBVideoObjectDTO.setHeight( lIBVideoObject.getHeight() );

        lIBVideoObjectDTO.setLength( lIBVideoObject.getLength() );

        lIBVideoObjectDTO.setBitrate( lIBVideoObject.getBitrate() );

        lIBVideoObjectDTO.setCodec( lIBVideoObject.getCodec() );

        lIBVideoObjectDTO.setQuality( lIBVideoObject.getQuality() );

        lIBVideoObjectDTO.setAspectRatio( lIBVideoObject.getAspectRatio() );

        return lIBVideoObjectDTO;
    }

    @Override

    public List<LIBVideoObjectDTO> lIBVideoObjectsToLIBVideoObjectDTOs(List<LIBVideoObject> lIBVideoObjects) {

        if ( lIBVideoObjects == null ) {

            return null;
        }

        List<LIBVideoObjectDTO> list = new ArrayList<LIBVideoObjectDTO>();

        for ( LIBVideoObject lIBVideoObject : lIBVideoObjects ) {

            list.add( lIBVideoObjectToLIBVideoObjectDTO( lIBVideoObject ) );
        }

        return list;
    }

    @Override

    public LIBVideoObject lIBVideoObjectDTOToLIBVideoObject(LIBVideoObjectDTO lIBVideoObjectDTO) {

        if ( lIBVideoObjectDTO == null ) {

            return null;
        }

        LIBVideoObject lIBVideoObject = new LIBVideoObject();

        lIBVideoObject.setMediaItem( lIBMediaItemFromId( lIBVideoObjectDTO.getMediaItemId() ) );

        lIBVideoObject.setCloudObject( lIBCloudObjectFromId( lIBVideoObjectDTO.getCloudObjectId() ) );

        lIBVideoObject.setId( lIBVideoObjectDTO.getId() );

        lIBVideoObject.setWidth( lIBVideoObjectDTO.getWidth() );

        lIBVideoObject.setHeight( lIBVideoObjectDTO.getHeight() );

        lIBVideoObject.setLength( lIBVideoObjectDTO.getLength() );

        lIBVideoObject.setBitrate( lIBVideoObjectDTO.getBitrate() );

        lIBVideoObject.setCodec( lIBVideoObjectDTO.getCodec() );

        lIBVideoObject.setQuality( lIBVideoObjectDTO.getQuality() );

        lIBVideoObject.setAspectRatio( lIBVideoObjectDTO.getAspectRatio() );

        return lIBVideoObject;
    }

    @Override

    public List<LIBVideoObject> lIBVideoObjectDTOsToLIBVideoObjects(List<LIBVideoObjectDTO> lIBVideoObjectDTOs) {

        if ( lIBVideoObjectDTOs == null ) {

            return null;
        }

        List<LIBVideoObject> list = new ArrayList<LIBVideoObject>();

        for ( LIBVideoObjectDTO lIBVideoObjectDTO : lIBVideoObjectDTOs ) {

            list.add( lIBVideoObjectDTOToLIBVideoObject( lIBVideoObjectDTO ) );
        }

        return list;
    }

    private Long lIBVideoObjectCloudObjectId(LIBVideoObject lIBVideoObject) {

        if ( lIBVideoObject == null ) {

            return null;
        }

        LIBCloudObject cloudObject = lIBVideoObject.getCloudObject();

        if ( cloudObject == null ) {

            return null;
        }

        Long id = cloudObject.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBVideoObjectMediaItemId(LIBVideoObject lIBVideoObject) {

        if ( lIBVideoObject == null ) {

            return null;
        }

        LIBMediaItem mediaItem = lIBVideoObject.getMediaItem();

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

