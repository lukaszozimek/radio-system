package io.protone.service.mapper;

import io.protone.domain.LIBFileItem;

import io.protone.domain.LIBLibrary;

import io.protone.service.dto.LIBFileItemDTO;

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

public class LIBFileItemMapperImpl implements LIBFileItemMapper {

    @Override

    public LIBFileItemDTO lIBFileItemToLIBFileItemDTO(LIBFileItem lIBFileItem) {

        if ( lIBFileItem == null ) {

            return null;
        }

        LIBFileItemDTO lIBFileItemDTO = new LIBFileItemDTO();

        lIBFileItemDTO.setParentFileId( lIBFileItemParentFileId( lIBFileItem ) );

        lIBFileItemDTO.setLibraryId( lIBFileItemLibraryId( lIBFileItem ) );

        lIBFileItemDTO.setId( lIBFileItem.getId() );

        lIBFileItemDTO.setIdx( lIBFileItem.getIdx() );

        lIBFileItemDTO.setName( lIBFileItem.getName() );

        lIBFileItemDTO.setType( lIBFileItem.getType() );

        return lIBFileItemDTO;
    }

    @Override

    public List<LIBFileItemDTO> lIBFileItemsToLIBFileItemDTOs(List<LIBFileItem> lIBFileItems) {

        if ( lIBFileItems == null ) {

            return null;
        }

        List<LIBFileItemDTO> list = new ArrayList<LIBFileItemDTO>();

        for ( LIBFileItem lIBFileItem : lIBFileItems ) {

            list.add( lIBFileItemToLIBFileItemDTO( lIBFileItem ) );
        }

        return list;
    }

    @Override

    public LIBFileItem lIBFileItemDTOToLIBFileItem(LIBFileItemDTO lIBFileItemDTO) {

        if ( lIBFileItemDTO == null ) {

            return null;
        }

        LIBFileItem lIBFileItem = new LIBFileItem();

        lIBFileItem.setParentFile( lIBFileItemFromId( lIBFileItemDTO.getParentFileId() ) );

        lIBFileItem.setLibrary( lIBLibraryFromId( lIBFileItemDTO.getLibraryId() ) );

        lIBFileItem.setId( lIBFileItemDTO.getId() );

        lIBFileItem.setIdx( lIBFileItemDTO.getIdx() );

        lIBFileItem.setName( lIBFileItemDTO.getName() );

        lIBFileItem.setType( lIBFileItemDTO.getType() );

        return lIBFileItem;
    }

    @Override

    public List<LIBFileItem> lIBFileItemDTOsToLIBFileItems(List<LIBFileItemDTO> lIBFileItemDTOs) {

        if ( lIBFileItemDTOs == null ) {

            return null;
        }

        List<LIBFileItem> list = new ArrayList<LIBFileItem>();

        for ( LIBFileItemDTO lIBFileItemDTO : lIBFileItemDTOs ) {

            list.add( lIBFileItemDTOToLIBFileItem( lIBFileItemDTO ) );
        }

        return list;
    }

    private Long lIBFileItemParentFileId(LIBFileItem lIBFileItem) {

        if ( lIBFileItem == null ) {

            return null;
        }

        LIBFileItem parentFile = lIBFileItem.getParentFile();

        if ( parentFile == null ) {

            return null;
        }

        Long id = parentFile.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long lIBFileItemLibraryId(LIBFileItem lIBFileItem) {

        if ( lIBFileItem == null ) {

            return null;
        }

        LIBLibrary library = lIBFileItem.getLibrary();

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

