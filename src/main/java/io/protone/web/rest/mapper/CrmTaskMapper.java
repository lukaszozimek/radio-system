package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmOpportunity;
import io.protone.domain.CrmTask;
import org.mapstruct.*;

import java.util.ArrayList;
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
    CrmTaskPT DB2DTO(CrmTask cORAddress);

    List<CrmTaskPT> DBs2DTOs(Set<CrmTask> cORAddresses);

    List<CrmTaskPT> DBs2DTOs(List<CrmTask> cORAddresses);

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
    CrmTask DTO2DB(CrmTaskPT cORAddressDTO, @Context CorNetwork corNetwork);

    default Set<CrmTask> DTOs2DBs(List<CrmTaskPT> crmTaskPTS, CorNetwork corNetwork) {
        Set<CrmTask> crmTasks = new HashSet<>();
        if (crmTaskPTS.isEmpty() || crmTaskPTS == null) {
            return null;
        }
        for (CrmTaskPT dto : crmTaskPTS) {
            crmTasks.add(DTO2DB(dto, corNetwork));
        }
        return crmTasks;
    }

    @AfterMapping
    default void crmTaskPTToCrmTaskAfterMapping(CrmTaskPT dto, @MappingTarget CrmTask entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}

