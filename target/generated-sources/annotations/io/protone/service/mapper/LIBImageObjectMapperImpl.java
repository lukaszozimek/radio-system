package io.protone.service.mapper;

import io.protone.domain.LIBCloudObject;

import io.protone.domain.LIBImageItem;

import io.protone.domain.LIBImageObject;

import io.protone.service.dto.LIBImageObjectDTO;

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

public class LIBImageObjectMapperImpl implements LIBImageObjectMapper {

    @Override

    public LIBImageObjectDTO lIBImageObjectToLIBImageObjectDTO(LIBImageObject lIBImageObject) {

        if ( lIBImageObject == null ) {

            return null;
        }

        LIBImageObjectDTO lIBImageObjectDTO = new LIBImageObjectDTO();

        lIBImageObjectDTO.setCloudObjectId( lIBImageObjectCloudObjectId( lIBImageObject ) );

        lIBImageObjectDTO.setImageItemId( lIBImageObjectImageItemId( lIBImageObject ) );

        lIBImageObjectDTO.setId( lIBImageObject.getId() );

        lIBImageObjectDTO.setWidth( lIBImageObject.getWidth() );

        lIBImageObjectDTO.setHeight( lIBImageObject.getHeight() );

        lIBImageObjectDTO.setImageSize( lIBImageObject.getImageSize() );

        return lIBImageObjectDTO;
    }

    @Override

    public List<LIBImageObjectDTO> lIBImageObjectsToLIBImageObjectDTOs(List<LIBImageObject> lIBImageObjects) {

        if ( lIBImageObjects == null ) {

            return null;
        }

        List<LIBImageObjectDTO> list = new ArrayList<LIBImageObjectDTO>();

        for ( LIBImageObject lIBImageObject : lIBImageObjects ) {

            list.add( lIBImageObjectToLIBImageObjectDTO( lIBImageObject ) );
        }

        return list;
    }

    @Override

    public LIBImageObject lIBImageObjectDTOToLIBImageObject(LIBImageObjectDTO lIBImageObjectDTO) {

        if ( lIBImageObjectDTO == null ) {

            return null;
        }

        LIBImageObject lIBImageObject = new LIBImageObject();

        lIBImageObject.setImageItem( lIBImageItemFromId( lIBImageObjectDTO.getImageItemId() ) );

        lIBImageObject.setCloudObject( lIBCloudObjectFromId( lIBImageObjectDTO.getCloudObjectId() ) );

        lIBImageObject.setId( lIBImageObjectDTO.getId() );

        lIBImageObject.setWidth( lIBImageObjectDTO.getWidth() );

        lIBImageObject.setHeight( lIBImageObjectDTO.getHeight() );

        lIBImageObject.setImageSize( lIBImageObjectDTO.getImageSize() );

        return lIBImageObject;
    }

    @Override

    public List<LIBImageObject> lIBImageObjectDTOsToLIBImageObjects(List<LIBImageObjectDTO> lIBImageObjectDTOs) {

        if ( lIBImageObjectDTOs == null ) {

            return null;
        }

        List<LIBImageObject> list = new ArrayList<LIBImageObject>();

        for ( LIBImageObjectDTO lIBImageObjectDTO : lIBImageObjectDTOs ) {

            list.add( lIBImageObjectDTOToLIBImageObject( lIBImageObjectDTO ) );
        }

        return list;
    }

    private Long lIBImageObjectCloudObjectId(LIBImageObject lIBImageObject) {

        if ( lIBImageObject == null ) {

            return null;
        }

        LIBCloudObject cloudObject = lIBImageObject.getCloudObject();

        if ( cloudObject == null ) {

            return null;
        }

        Long id = cloudObject.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBImageObjectImageItemId(LIBImageObject lIBImageObject) {

        if ( lIBImageObject == null ) {

            return null;
        }

        LIBImageItem imageItem = lIBImageObject.getImageItem();

        if ( imageItem == null ) {

            return null;
        }

        Long id = imageItem.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

