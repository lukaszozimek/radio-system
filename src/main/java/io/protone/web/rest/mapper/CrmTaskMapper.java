package io.protone.web.rest.mapper;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmTask;
import io.protone.web.rest.dto.crm.CrmTaskDTO;
import io.protone.web.rest.dto.crm.thin.CrmTaskThinDTO;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CorUserMapper.class, CrmTaskCommentMapper.class})
public interface CrmTaskMapper {

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
    @Mapping(source = "comments", target = "comments")
    CrmTaskDTO DB2DTO(CrmTask crmTask);

    List<CrmTaskDTO> DBs2DTOs(Set<CrmTask> crmTasks);

    List<CrmTaskDTO> DBs2DTOs(List<CrmTask> crmTasks);


    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
    CrmTaskThinDTO DB2ThinDTO(CrmTask crmTask);

    List<CrmTaskThinDTO> DBs2ThinDTOs(Set<CrmTaskThinDTO> crmTasks);

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
    @Mapping(source = "comments", target = "comments")
    CrmTask DTO2DB(CrmTaskDTO cORAddressDTO, @Context CorNetwork corNetwork);

    default Set<CrmTask> DTOs2DBs(List<CrmTaskDTO> crmTaskDTOS, CorNetwork corNetwork) {
        Set<CrmTask> crmTasks = new HashSet<>();
        if (crmTaskDTOS.isEmpty() || crmTaskDTOS == null) {
            return null;
        }
        for (CrmTaskDTO dto : crmTaskDTOS) {
            crmTasks.add(DTO2DB(dto, corNetwork));
        }
        return crmTasks;
    }

    @AfterMapping
    default void crmTaskPTToCrmTaskAfterMapping(CrmTaskDTO dto, @MappingTarget CrmTask entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}

