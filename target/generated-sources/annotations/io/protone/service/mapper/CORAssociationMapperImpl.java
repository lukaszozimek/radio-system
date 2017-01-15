package io.protone.service.mapper;

import io.protone.domain.CORAssociation;

import io.protone.domain.CORNetwork;

import io.protone.service.dto.CORAssociationDTO;

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

public class CORAssociationMapperImpl implements CORAssociationMapper {

    @Override

    public CORAssociationDTO cORAssociationToCORAssociationDTO(CORAssociation cORAssociation) {

        if ( cORAssociation == null ) {

            return null;
        }

        CORAssociationDTO cORAssociationDTO = new CORAssociationDTO();

        cORAssociationDTO.setNetworkId( cORAssociationNetworkId( cORAssociation ) );

        cORAssociationDTO.setId( cORAssociation.getId() );

        cORAssociationDTO.setName( cORAssociation.getName() );

        cORAssociationDTO.setSourceClass( cORAssociation.getSourceClass() );

        cORAssociationDTO.setSourceId( cORAssociation.getSourceId() );

        cORAssociationDTO.setTargetClass( cORAssociation.getTargetClass() );

        cORAssociationDTO.setTargetId( cORAssociation.getTargetId() );

        return cORAssociationDTO;
    }

    @Override

    public List<CORAssociationDTO> cORAssociationsToCORAssociationDTOs(List<CORAssociation> cORAssociations) {

        if ( cORAssociations == null ) {

            return null;
        }

        List<CORAssociationDTO> list = new ArrayList<CORAssociationDTO>();

        for ( CORAssociation cORAssociation : cORAssociations ) {

            list.add( cORAssociationToCORAssociationDTO( cORAssociation ) );
        }

        return list;
    }

    @Override

    public CORAssociation cORAssociationDTOToCORAssociation(CORAssociationDTO cORAssociationDTO) {

        if ( cORAssociationDTO == null ) {

            return null;
        }

        CORAssociation cORAssociation = new CORAssociation();

        cORAssociation.setNetwork( cORNetworkFromId( cORAssociationDTO.getNetworkId() ) );

        cORAssociation.setId( cORAssociationDTO.getId() );

        cORAssociation.setName( cORAssociationDTO.getName() );

        cORAssociation.setSourceClass( cORAssociationDTO.getSourceClass() );

        cORAssociation.setSourceId( cORAssociationDTO.getSourceId() );

        cORAssociation.setTargetClass( cORAssociationDTO.getTargetClass() );

        cORAssociation.setTargetId( cORAssociationDTO.getTargetId() );

        return cORAssociation;
    }

    @Override

    public List<CORAssociation> cORAssociationDTOsToCORAssociations(List<CORAssociationDTO> cORAssociationDTOs) {

        if ( cORAssociationDTOs == null ) {

            return null;
        }

        List<CORAssociation> list = new ArrayList<CORAssociation>();

        for ( CORAssociationDTO cORAssociationDTO : cORAssociationDTOs ) {

            list.add( cORAssociationDTOToCORAssociation( cORAssociationDTO ) );
        }

        return list;
    }

    private Long cORAssociationNetworkId(CORAssociation cORAssociation) {

        if ( cORAssociation == null ) {

            return null;
        }

        CORNetwork network = cORAssociation.getNetwork();

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

