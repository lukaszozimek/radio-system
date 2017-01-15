package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CRMStage;

import io.protone.service.dto.CRMStageDTO;

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

public class CRMStageMapperImpl implements CRMStageMapper {

    @Override

    public CRMStageDTO cRMStageToCRMStageDTO(CRMStage cRMStage) {

        if ( cRMStage == null ) {

            return null;
        }

        CRMStageDTO cRMStageDTO = new CRMStageDTO();

        cRMStageDTO.setNetworkId( cRMStageNetworkId( cRMStage ) );

        cRMStageDTO.setId( cRMStage.getId() );

        cRMStageDTO.setName( cRMStage.getName() );

        return cRMStageDTO;
    }

    @Override

    public List<CRMStageDTO> cRMStagesToCRMStageDTOs(List<CRMStage> cRMStages) {

        if ( cRMStages == null ) {

            return null;
        }

        List<CRMStageDTO> list = new ArrayList<CRMStageDTO>();

        for ( CRMStage cRMStage : cRMStages ) {

            list.add( cRMStageToCRMStageDTO( cRMStage ) );
        }

        return list;
    }

    @Override

    public CRMStage cRMStageDTOToCRMStage(CRMStageDTO cRMStageDTO) {

        if ( cRMStageDTO == null ) {

            return null;
        }

        CRMStage cRMStage = new CRMStage();

        cRMStage.setNetwork( cORNetworkFromId( cRMStageDTO.getNetworkId() ) );

        cRMStage.setId( cRMStageDTO.getId() );

        cRMStage.setName( cRMStageDTO.getName() );

        return cRMStage;
    }

    @Override

    public List<CRMStage> cRMStageDTOsToCRMStages(List<CRMStageDTO> cRMStageDTOs) {

        if ( cRMStageDTOs == null ) {

            return null;
        }

        List<CRMStage> list = new ArrayList<CRMStage>();

        for ( CRMStageDTO cRMStageDTO : cRMStageDTOs ) {

            list.add( cRMStageDTOToCRMStage( cRMStageDTO ) );
        }

        return list;
    }

    private Long cRMStageNetworkId(CRMStage cRMStage) {

        if ( cRMStage == null ) {

            return null;
        }

        CORNetwork network = cRMStage.getNetwork();

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

