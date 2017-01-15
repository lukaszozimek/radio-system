package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.LIBLibrary;

import io.protone.service.dto.LIBLibraryDTO;

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

public class LIBLibraryMapperImpl implements LIBLibraryMapper {

    @Override

    public LIBLibraryDTO lIBLibraryToLIBLibraryDTO(LIBLibrary lIBLibrary) {

        if ( lIBLibrary == null ) {

            return null;
        }

        LIBLibraryDTO lIBLibraryDTO = new LIBLibraryDTO();

        lIBLibraryDTO.setNetworkId( lIBLibraryNetworkId( lIBLibrary ) );

        lIBLibraryDTO.setId( lIBLibrary.getId() );

        lIBLibraryDTO.setPrefix( lIBLibrary.getPrefix() );

        lIBLibraryDTO.setIdxLength( lIBLibrary.getIdxLength() );

        lIBLibraryDTO.setShortcut( lIBLibrary.getShortcut() );

        lIBLibraryDTO.setName( lIBLibrary.getName() );

        lIBLibraryDTO.setCounter( lIBLibrary.getCounter() );

        lIBLibraryDTO.setCounterType( lIBLibrary.getCounterType() );

        lIBLibraryDTO.setLibraryType( lIBLibrary.getLibraryType() );

        lIBLibraryDTO.setDescription( lIBLibrary.getDescription() );

        return lIBLibraryDTO;
    }

    @Override

    public List<LIBLibraryDTO> lIBLibrariesToLIBLibraryDTOs(List<LIBLibrary> lIBLibraries) {

        if ( lIBLibraries == null ) {

            return null;
        }

        List<LIBLibraryDTO> list = new ArrayList<LIBLibraryDTO>();

        for ( LIBLibrary lIBLibrary : lIBLibraries ) {

            list.add( lIBLibraryToLIBLibraryDTO( lIBLibrary ) );
        }

        return list;
    }

    @Override

    public LIBLibrary lIBLibraryDTOToLIBLibrary(LIBLibraryDTO lIBLibraryDTO) {

        if ( lIBLibraryDTO == null ) {

            return null;
        }

        LIBLibrary lIBLibrary = new LIBLibrary();

        lIBLibrary.setNetwork( cORNetworkFromId( lIBLibraryDTO.getNetworkId() ) );

        lIBLibrary.setId( lIBLibraryDTO.getId() );

        lIBLibrary.setPrefix( lIBLibraryDTO.getPrefix() );

        lIBLibrary.setIdxLength( lIBLibraryDTO.getIdxLength() );

        lIBLibrary.setShortcut( lIBLibraryDTO.getShortcut() );

        lIBLibrary.setName( lIBLibraryDTO.getName() );

        lIBLibrary.setCounter( lIBLibraryDTO.getCounter() );

        lIBLibrary.setCounterType( lIBLibraryDTO.getCounterType() );

        lIBLibrary.setLibraryType( lIBLibraryDTO.getLibraryType() );

        lIBLibrary.setDescription( lIBLibraryDTO.getDescription() );

        return lIBLibrary;
    }

    @Override

    public List<LIBLibrary> lIBLibraryDTOsToLIBLibraries(List<LIBLibraryDTO> lIBLibraryDTOs) {

        if ( lIBLibraryDTOs == null ) {

            return null;
        }

        List<LIBLibrary> list = new ArrayList<LIBLibrary>();

        for ( LIBLibraryDTO lIBLibraryDTO : lIBLibraryDTOs ) {

            list.add( lIBLibraryDTOToLIBLibrary( lIBLibraryDTO ) );
        }

        return list;
    }

    private Long lIBLibraryNetworkId(LIBLibrary lIBLibrary) {

        if ( lIBLibrary == null ) {

            return null;
        }

        CORNetwork network = lIBLibrary.getNetwork();

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

