package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.CrmTaskDTO;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmTask;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CorUserMapper.class})
public interface CrmTaskMapper {

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
    CrmTaskDTO DB2DTO(CrmTask cORAddress);

    List<CrmTaskDTO> DBs2DTOs(Set<CrmTask> cORAddresses);

    List<CrmTaskDTO> DBs2DTOs(List<CrmTask> cORAddresses);

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
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

