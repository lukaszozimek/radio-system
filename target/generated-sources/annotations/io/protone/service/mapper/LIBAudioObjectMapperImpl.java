package io.protone.service.mapper;

import io.protone.domain.LIBAudioObject;

import io.protone.domain.LIBCloudObject;

import io.protone.domain.LIBMediaItem;

import io.protone.service.dto.LIBAudioObjectDTO;

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

public class LIBAudioObjectMapperImpl implements LIBAudioObjectMapper {

    @Override

    public LIBAudioObjectDTO lIBAudioObjectToLIBAudioObjectDTO(LIBAudioObject lIBAudioObject) {

        if ( lIBAudioObject == null ) {

            return null;
        }

        LIBAudioObjectDTO lIBAudioObjectDTO = new LIBAudioObjectDTO();

        lIBAudioObjectDTO.setCloudObjectId( lIBAudioObjectCloudObjectId( lIBAudioObject ) );

        lIBAudioObjectDTO.setMediaItemId( lIBAudioObjectMediaItemId( lIBAudioObject ) );

        lIBAudioObjectDTO.setId( lIBAudioObject.getId() );

        lIBAudioObjectDTO.setLength( lIBAudioObject.getLength() );

        lIBAudioObjectDTO.setBitrate( lIBAudioObject.getBitrate() );

        lIBAudioObjectDTO.setCodec( lIBAudioObject.getCodec() );

        lIBAudioObjectDTO.setQuality( lIBAudioObject.getQuality() );

        return lIBAudioObjectDTO;
    }

    @Override

    public List<LIBAudioObjectDTO> lIBAudioObjectsToLIBAudioObjectDTOs(List<LIBAudioObject> lIBAudioObjects) {

        if ( lIBAudioObjects == null ) {

            return null;
        }

        List<LIBAudioObjectDTO> list = new ArrayList<LIBAudioObjectDTO>();

        for ( LIBAudioObject lIBAudioObject : lIBAudioObjects ) {

            list.add( lIBAudioObjectToLIBAudioObjectDTO( lIBAudioObject ) );
        }

        return list;
    }

    @Override

    public LIBAudioObject lIBAudioObjectDTOToLIBAudioObject(LIBAudioObjectDTO lIBAudioObjectDTO) {

        if ( lIBAudioObjectDTO == null ) {

            return null;
        }

        LIBAudioObject lIBAudioObject = new LIBAudioObject();

        lIBAudioObject.setMediaItem( lIBMediaItemFromId( lIBAudioObjectDTO.getMediaItemId() ) );

        lIBAudioObject.setCloudObject( lIBCloudObjectFromId( lIBAudioObjectDTO.getCloudObjectId() ) );

        lIBAudioObject.setId( lIBAudioObjectDTO.getId() );

        lIBAudioObject.setLength( lIBAudioObjectDTO.getLength() );

        lIBAudioObject.setBitrate( lIBAudioObjectDTO.getBitrate() );

        lIBAudioObject.setCodec( lIBAudioObjectDTO.getCodec() );

        lIBAudioObject.setQuality( lIBAudioObjectDTO.getQuality() );

        return lIBAudioObject;
    }

    @Override

    public List<LIBAudioObject> lIBAudioObjectDTOsToLIBAudioObjects(List<LIBAudioObjectDTO> lIBAudioObjectDTOs) {

        if ( lIBAudioObjectDTOs == null ) {

            return null;
        }

        List<LIBAudioObject> list = new ArrayList<LIBAudioObject>();

        for ( LIBAudioObjectDTO lIBAudioObjectDTO : lIBAudioObjectDTOs ) {

            list.add( lIBAudioObjectDTOToLIBAudioObject( lIBAudioObjectDTO ) );
        }

        return list;
    }

    private Long lIBAudioObjectCloudObjectId(LIBAudioObject lIBAudioObject) {

        if ( lIBAudioObject == null ) {

            return null;
        }

        LIBCloudObject cloudObject = lIBAudioObject.getCloudObject();

        if ( cloudObject == null ) {

            return null;
        }

        Long id = cloudObject.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBAudioObjectMediaItemId(LIBAudioObject lIBAudioObject) {

        if ( lIBAudioObject == null ) {

            return null;
        }

        LIBMediaItem mediaItem = lIBAudioObject.getMediaItem();

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

