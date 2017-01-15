package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CRMLeadStatus;

import io.protone.service.dto.CRMLeadStatusDTO;

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

public class CRMLeadStatusMapperImpl implements CRMLeadStatusMapper {

    @Override

    public CRMLeadStatusDTO cRMLeadStatusToCRMLeadStatusDTO(CRMLeadStatus cRMLeadStatus) {

        if ( cRMLeadStatus == null ) {

            return null;
        }

        CRMLeadStatusDTO cRMLeadStatusDTO = new CRMLeadStatusDTO();

        cRMLeadStatusDTO.setNetworkId( cRMLeadStatusNetworkId( cRMLeadStatus ) );

        cRMLeadStatusDTO.setId( cRMLeadStatus.getId() );

        cRMLeadStatusDTO.setName( cRMLeadStatus.getName() );

        return cRMLeadStatusDTO;
    }

    @Override

    public List<CRMLeadStatusDTO> cRMLeadStatusesToCRMLeadStatusDTOs(List<CRMLeadStatus> cRMLeadStatuses) {

        if ( cRMLeadStatuses == null ) {

            return null;
        }

        List<CRMLeadStatusDTO> list = new ArrayList<CRMLeadStatusDTO>();

        for ( CRMLeadStatus cRMLeadStatus : cRMLeadStatuses ) {

            list.add( cRMLeadStatusToCRMLeadStatusDTO( cRMLeadStatus ) );
        }

        return list;
    }

    @Override

    public CRMLeadStatus cRMLeadStatusDTOToCRMLeadStatus(CRMLeadStatusDTO cRMLeadStatusDTO) {

        if ( cRMLeadStatusDTO == null ) {

            return null;
        }

        CRMLeadStatus cRMLeadStatus = new CRMLeadStatus();

        cRMLeadStatus.setNetwork( cORNetworkFromId( cRMLeadStatusDTO.getNetworkId() ) );

        cRMLeadStatus.setId( cRMLeadStatusDTO.getId() );

        cRMLeadStatus.setName( cRMLeadStatusDTO.getName() );

        return cRMLeadStatus;
    }

    @Override

    public List<CRMLeadStatus> cRMLeadStatusDTOsToCRMLeadStatuses(List<CRMLeadStatusDTO> cRMLeadStatusDTOs) {

        if ( cRMLeadStatusDTOs == null ) {

            return null;
        }

        List<CRMLeadStatus> list = new ArrayList<CRMLeadStatus>();

        for ( CRMLeadStatusDTO cRMLeadStatusDTO : cRMLeadStatusDTOs ) {

            list.add( cRMLeadStatusDTOToCRMLeadStatus( cRMLeadStatusDTO ) );
        }

        return list;
    }

    private Long cRMLeadStatusNetworkId(CRMLeadStatus cRMLeadStatus) {

        if ( cRMLeadStatus == null ) {

            return null;
        }

        CORNetwork network = cRMLeadStatus.getNetwork();

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

