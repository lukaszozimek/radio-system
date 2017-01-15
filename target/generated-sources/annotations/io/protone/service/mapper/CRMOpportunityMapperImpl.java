package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CRMOpportunity;

import io.protone.service.dto.CRMOpportunityDTO;

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

public class CRMOpportunityMapperImpl implements CRMOpportunityMapper {

    @Override

    public CRMOpportunityDTO cRMOpportunityToCRMOpportunityDTO(CRMOpportunity cRMOpportunity) {

        if ( cRMOpportunity == null ) {

            return null;
        }

        CRMOpportunityDTO cRMOpportunityDTO = new CRMOpportunityDTO();

        cRMOpportunityDTO.setNetworkId( cRMOpportunityNetworkId( cRMOpportunity ) );

        cRMOpportunityDTO.setId( cRMOpportunity.getId() );

        cRMOpportunityDTO.setName( cRMOpportunity.getName() );

        cRMOpportunityDTO.setLastTry( cRMOpportunity.getLastTry() );

        cRMOpportunityDTO.setCloseDate( cRMOpportunity.getCloseDate() );

        cRMOpportunityDTO.setProbability( cRMOpportunity.getProbability() );

        return cRMOpportunityDTO;
    }

    @Override

    public List<CRMOpportunityDTO> cRMOpportunitiesToCRMOpportunityDTOs(List<CRMOpportunity> cRMOpportunities) {

        if ( cRMOpportunities == null ) {

            return null;
        }

        List<CRMOpportunityDTO> list = new ArrayList<CRMOpportunityDTO>();

        for ( CRMOpportunity cRMOpportunity : cRMOpportunities ) {

            list.add( cRMOpportunityToCRMOpportunityDTO( cRMOpportunity ) );
        }

        return list;
    }

    @Override

    public CRMOpportunity cRMOpportunityDTOToCRMOpportunity(CRMOpportunityDTO cRMOpportunityDTO) {

        if ( cRMOpportunityDTO == null ) {

            return null;
        }

        CRMOpportunity cRMOpportunity = new CRMOpportunity();

        cRMOpportunity.setNetwork( cORNetworkFromId( cRMOpportunityDTO.getNetworkId() ) );

        cRMOpportunity.setId( cRMOpportunityDTO.getId() );

        cRMOpportunity.setName( cRMOpportunityDTO.getName() );

        cRMOpportunity.setLastTry( cRMOpportunityDTO.getLastTry() );

        cRMOpportunity.setCloseDate( cRMOpportunityDTO.getCloseDate() );

        cRMOpportunity.setProbability( cRMOpportunityDTO.getProbability() );

        return cRMOpportunity;
    }

    @Override

    public List<CRMOpportunity> cRMOpportunityDTOsToCRMOpportunities(List<CRMOpportunityDTO> cRMOpportunityDTOs) {

        if ( cRMOpportunityDTOs == null ) {

            return null;
        }

        List<CRMOpportunity> list = new ArrayList<CRMOpportunity>();

        for ( CRMOpportunityDTO cRMOpportunityDTO : cRMOpportunityDTOs ) {

            list.add( cRMOpportunityDTOToCRMOpportunity( cRMOpportunityDTO ) );
        }

        return list;
    }

    private Long cRMOpportunityNetworkId(CRMOpportunity cRMOpportunity) {

        if ( cRMOpportunity == null ) {

            return null;
        }

        CORNetwork network = cRMOpportunity.getNetwork();

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

