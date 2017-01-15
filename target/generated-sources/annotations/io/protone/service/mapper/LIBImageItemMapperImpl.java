package io.protone.service.mapper;

import io.protone.domain.LIBImageItem;

import io.protone.domain.LIBLibrary;

import io.protone.service.dto.LIBImageItemDTO;

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

public class LIBImageItemMapperImpl implements LIBImageItemMapper {

    @Override

    public LIBImageItemDTO lIBImageItemToLIBImageItemDTO(LIBImageItem lIBImageItem) {

        if ( lIBImageItem == null ) {

            return null;
        }

        LIBImageItemDTO lIBImageItemDTO = new LIBImageItemDTO();

        lIBImageItemDTO.setLibraryId( lIBImageItemLibraryId( lIBImageItem ) );

        lIBImageItemDTO.setId( lIBImageItem.getId() );

        lIBImageItemDTO.setIdx( lIBImageItem.getIdx() );

        lIBImageItemDTO.setName( lIBImageItem.getName() );

        return lIBImageItemDTO;
    }

    @Override

    public List<LIBImageItemDTO> lIBImageItemsToLIBImageItemDTOs(List<LIBImageItem> lIBImageItems) {

        if ( lIBImageItems == null ) {

            return null;
        }

        List<LIBImageItemDTO> list = new ArrayList<LIBImageItemDTO>();

        for ( LIBImageItem lIBImageItem : lIBImageItems ) {

            list.add( lIBImageItemToLIBImageItemDTO( lIBImageItem ) );
        }

        return list;
    }

    @Override

    public LIBImageItem lIBImageItemDTOToLIBImageItem(LIBImageItemDTO lIBImageItemDTO) {

        if ( lIBImageItemDTO == null ) {

            return null;
        }

        LIBImageItem lIBImageItem = new LIBImageItem();

        lIBImageItem.setLibrary( lIBLibraryFromId( lIBImageItemDTO.getLibraryId() ) );

        lIBImageItem.setId( lIBImageItemDTO.getId() );

        lIBImageItem.setIdx( lIBImageItemDTO.getIdx() );

        lIBImageItem.setName( lIBImageItemDTO.getName() );

        return lIBImageItem;
    }

    @Override

    public List<LIBImageItem> lIBImageItemDTOsToLIBImageItems(List<LIBImageItemDTO> lIBImageItemDTOs) {

        if ( lIBImageItemDTOs == null ) {

            return null;
        }

        List<LIBImageItem> list = new ArrayList<LIBImageItem>();

        for ( LIBImageItemDTO lIBImageItemDTO : lIBImageItemDTOs ) {

            list.add( lIBImageItemDTOToLIBImageItem( lIBImageItemDTO ) );
        }

        return list;
    }

    private Long lIBImageItemLibraryId(LIBImageItem lIBImageItem) {

        if ( lIBImageItem == null ) {

            return null;
        }

        LIBLibrary library = lIBImageItem.getLibrary();

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

