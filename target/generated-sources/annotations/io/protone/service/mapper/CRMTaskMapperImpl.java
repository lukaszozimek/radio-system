package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CRMTask;

import io.protone.service.dto.CRMTaskDTO;

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

public class CRMTaskMapperImpl implements CRMTaskMapper {

    @Override

    public CRMTaskDTO cRMTaskToCRMTaskDTO(CRMTask cRMTask) {

        if ( cRMTask == null ) {

            return null;
        }

        CRMTaskDTO cRMTaskDTO = new CRMTaskDTO();

        cRMTaskDTO.setNetworkId( cRMTaskNetworkId( cRMTask ) );

        cRMTaskDTO.setId( cRMTask.getId() );

        cRMTaskDTO.setSubject( cRMTask.getSubject() );

        cRMTaskDTO.setActivityDate( cRMTask.getActivityDate() );

        cRMTaskDTO.setActivityLength( cRMTask.getActivityLength() );

        cRMTaskDTO.setComment( cRMTask.getComment() );

        return cRMTaskDTO;
    }

    @Override

    public List<CRMTaskDTO> cRMTasksToCRMTaskDTOs(List<CRMTask> cRMTasks) {

        if ( cRMTasks == null ) {

            return null;
        }

        List<CRMTaskDTO> list = new ArrayList<CRMTaskDTO>();

        for ( CRMTask cRMTask : cRMTasks ) {

            list.add( cRMTaskToCRMTaskDTO( cRMTask ) );
        }

        return list;
    }

    @Override

    public CRMTask cRMTaskDTOToCRMTask(CRMTaskDTO cRMTaskDTO) {

        if ( cRMTaskDTO == null ) {

            return null;
        }

        CRMTask cRMTask = new CRMTask();

        cRMTask.setNetwork( cORNetworkFromId( cRMTaskDTO.getNetworkId() ) );

        cRMTask.setId( cRMTaskDTO.getId() );

        cRMTask.setSubject( cRMTaskDTO.getSubject() );

        cRMTask.setActivityDate( cRMTaskDTO.getActivityDate() );

        cRMTask.setActivityLength( cRMTaskDTO.getActivityLength() );

        cRMTask.setComment( cRMTaskDTO.getComment() );

        return cRMTask;
    }

    @Override

    public List<CRMTask> cRMTaskDTOsToCRMTasks(List<CRMTaskDTO> cRMTaskDTOs) {

        if ( cRMTaskDTOs == null ) {

            return null;
        }

        List<CRMTask> list = new ArrayList<CRMTask>();

        for ( CRMTaskDTO cRMTaskDTO : cRMTaskDTOs ) {

            list.add( cRMTaskDTOToCRMTask( cRMTaskDTO ) );
        }

        return list;
    }

    private Long cRMTaskNetworkId(CRMTask cRMTask) {

        if ( cRMTask == null ) {

            return null;
        }

        CORNetwork network = cRMTask.getNetwork();

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

