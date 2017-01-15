package io.protone.service.mapper;

import io.protone.domain.CORDocument;

import io.protone.domain.CORNetwork;

import io.protone.service.dto.CORDocumentDTO;

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

public class CORDocumentMapperImpl implements CORDocumentMapper {

    @Override

    public CORDocumentDTO cORDocumentToCORDocumentDTO(CORDocument cORDocument) {

        if ( cORDocument == null ) {

            return null;
        }

        CORDocumentDTO cORDocumentDTO = new CORDocumentDTO();

        cORDocumentDTO.setNetworkId( cORDocumentNetworkId( cORDocument ) );

        cORDocumentDTO.setId( cORDocument.getId() );

        cORDocumentDTO.setKey( cORDocument.getKey() );

        cORDocumentDTO.setDescription( cORDocument.getDescription() );

        cORDocumentDTO.setValue( cORDocument.getValue() );

        return cORDocumentDTO;
    }

    @Override

    public List<CORDocumentDTO> cORDocumentsToCORDocumentDTOs(List<CORDocument> cORDocuments) {

        if ( cORDocuments == null ) {

            return null;
        }

        List<CORDocumentDTO> list = new ArrayList<CORDocumentDTO>();

        for ( CORDocument cORDocument : cORDocuments ) {

            list.add( cORDocumentToCORDocumentDTO( cORDocument ) );
        }

        return list;
    }

    @Override

    public CORDocument cORDocumentDTOToCORDocument(CORDocumentDTO cORDocumentDTO) {

        if ( cORDocumentDTO == null ) {

            return null;
        }

        CORDocument cORDocument = new CORDocument();

        cORDocument.setNetwork( cORNetworkFromId( cORDocumentDTO.getNetworkId() ) );

        cORDocument.setId( cORDocumentDTO.getId() );

        cORDocument.setKey( cORDocumentDTO.getKey() );

        cORDocument.setDescription( cORDocumentDTO.getDescription() );

        cORDocument.setValue( cORDocumentDTO.getValue() );

        return cORDocument;
    }

    @Override

    public List<CORDocument> cORDocumentDTOsToCORDocuments(List<CORDocumentDTO> cORDocumentDTOs) {

        if ( cORDocumentDTOs == null ) {

            return null;
        }

        List<CORDocument> list = new ArrayList<CORDocument>();

        for ( CORDocumentDTO cORDocumentDTO : cORDocumentDTOs ) {

            list.add( cORDocumentDTOToCORDocument( cORDocumentDTO ) );
        }

        return list;
    }

    private Long cORDocumentNetworkId(CORDocument cORDocument) {

        if ( cORDocument == null ) {

            return null;
        }

        CORNetwork network = cORDocument.getNetwork();

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

