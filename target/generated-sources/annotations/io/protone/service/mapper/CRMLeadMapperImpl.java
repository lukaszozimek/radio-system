package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CRMLead;

import io.protone.service.dto.CRMLeadDTO;

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

public class CRMLeadMapperImpl implements CRMLeadMapper {

    @Override

    public CRMLeadDTO cRMLeadToCRMLeadDTO(CRMLead cRMLead) {

        if ( cRMLead == null ) {

            return null;
        }

        CRMLeadDTO cRMLeadDTO = new CRMLeadDTO();

        cRMLeadDTO.setNetworkId( cRMLeadNetworkId( cRMLead ) );

        cRMLeadDTO.setId( cRMLead.getId() );

        cRMLeadDTO.setName( cRMLead.getName() );

        cRMLeadDTO.setDescription( cRMLead.getDescription() );

        return cRMLeadDTO;
    }

    @Override

    public List<CRMLeadDTO> cRMLeadsToCRMLeadDTOs(List<CRMLead> cRMLeads) {

        if ( cRMLeads == null ) {

            return null;
        }

        List<CRMLeadDTO> list = new ArrayList<CRMLeadDTO>();

        for ( CRMLead cRMLead : cRMLeads ) {

            list.add( cRMLeadToCRMLeadDTO( cRMLead ) );
        }

        return list;
    }

    @Override

    public CRMLead cRMLeadDTOToCRMLead(CRMLeadDTO cRMLeadDTO) {

        if ( cRMLeadDTO == null ) {

            return null;
        }

        CRMLead cRMLead = new CRMLead();

        cRMLead.setNetwork( cORNetworkFromId( cRMLeadDTO.getNetworkId() ) );

        cRMLead.setId( cRMLeadDTO.getId() );

        cRMLead.setName( cRMLeadDTO.getName() );

        cRMLead.setDescription( cRMLeadDTO.getDescription() );

        return cRMLead;
    }

    @Override

    public List<CRMLead> cRMLeadDTOsToCRMLeads(List<CRMLeadDTO> cRMLeadDTOs) {

        if ( cRMLeadDTOs == null ) {

            return null;
        }

        List<CRMLead> list = new ArrayList<CRMLead>();

        for ( CRMLeadDTO cRMLeadDTO : cRMLeadDTOs ) {

            list.add( cRMLeadDTOToCRMLead( cRMLeadDTO ) );
        }

        return list;
    }

    private Long cRMLeadNetworkId(CRMLead cRMLead) {

        if ( cRMLead == null ) {

            return null;
        }

        CORNetwork network = cRMLead.getNetwork();

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

