package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CRMTaskStatus;

import io.protone.service.dto.CRMTaskStatusDTO;

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

public class CRMTaskStatusMapperImpl implements CRMTaskStatusMapper {

    @Override

    public CRMTaskStatusDTO cRMTaskStatusToCRMTaskStatusDTO(CRMTaskStatus cRMTaskStatus) {

        if ( cRMTaskStatus == null ) {

            return null;
        }

        CRMTaskStatusDTO cRMTaskStatusDTO = new CRMTaskStatusDTO();

        cRMTaskStatusDTO.setNetworkId( cRMTaskStatusNetworkId( cRMTaskStatus ) );

        cRMTaskStatusDTO.setId( cRMTaskStatus.getId() );

        cRMTaskStatusDTO.setName( cRMTaskStatus.getName() );

        return cRMTaskStatusDTO;
    }

    @Override

    public List<CRMTaskStatusDTO> cRMTaskStatusesToCRMTaskStatusDTOs(List<CRMTaskStatus> cRMTaskStatuses) {

        if ( cRMTaskStatuses == null ) {

            return null;
        }

        List<CRMTaskStatusDTO> list = new ArrayList<CRMTaskStatusDTO>();

        for ( CRMTaskStatus cRMTaskStatus : cRMTaskStatuses ) {

            list.add( cRMTaskStatusToCRMTaskStatusDTO( cRMTaskStatus ) );
        }

        return list;
    }

    @Override

    public CRMTaskStatus cRMTaskStatusDTOToCRMTaskStatus(CRMTaskStatusDTO cRMTaskStatusDTO) {

        if ( cRMTaskStatusDTO == null ) {

            return null;
        }

        CRMTaskStatus cRMTaskStatus = new CRMTaskStatus();

        cRMTaskStatus.setNetwork( cORNetworkFromId( cRMTaskStatusDTO.getNetworkId() ) );

        cRMTaskStatus.setId( cRMTaskStatusDTO.getId() );

        cRMTaskStatus.setName( cRMTaskStatusDTO.getName() );

        return cRMTaskStatus;
    }

    @Override

    public List<CRMTaskStatus> cRMTaskStatusDTOsToCRMTaskStatuses(List<CRMTaskStatusDTO> cRMTaskStatusDTOs) {

        if ( cRMTaskStatusDTOs == null ) {

            return null;
        }

        List<CRMTaskStatus> list = new ArrayList<CRMTaskStatus>();

        for ( CRMTaskStatusDTO cRMTaskStatusDTO : cRMTaskStatusDTOs ) {

            list.add( cRMTaskStatusDTOToCRMTaskStatus( cRMTaskStatusDTO ) );
        }

        return list;
    }

    private Long cRMTaskStatusNetworkId(CRMTaskStatus cRMTaskStatus) {

        if ( cRMTaskStatus == null ) {

            return null;
        }

        CORNetwork network = cRMTaskStatus.getNetwork();

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

