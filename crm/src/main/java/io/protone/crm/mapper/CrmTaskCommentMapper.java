package io.protone.crm.mapper;

import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorUserMapper;
import io.protone.crm.api.dto.CrmTaskCommentDTO;
import io.protone.crm.domain.CrmTaskComment;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CorUserMapper.class})
public interface CrmTaskCommentMapper {

    @Mapping(source = "createdBy", target = "createdBy")
    CrmTaskCommentDTO DB2DTO(CrmTaskComment crmTaskComment);

    List<CrmTaskCommentDTO> DBs2DTOs(Set<CrmTaskComment> crmTaskComments);

    List<CrmTaskCommentDTO> DBs2DTOs(List<CrmTaskComment> crmTaskComments);

    @Mapping(source = "createdBy", target = "createdBy")
    CrmTaskComment DTO2DB(CrmTaskCommentDTO crmTaskCommentDTO, @Context CorNetwork corNetwork);

    default Set<CrmTaskComment> DTOs2DBs(List<CrmTaskCommentDTO> crmTaskDTOS, CorNetwork corNetwork) {
        Set<CrmTaskComment> crmTasks = new HashSet<>();
        if (crmTaskDTOS.isEmpty() || crmTaskDTOS == null) {
            return null;
        }
        for (CrmTaskCommentDTO dto : crmTaskDTOS) {
            crmTasks.add(DTO2DB(dto, corNetwork));
        }
        return crmTasks;
    }

    @AfterMapping
    default void crmTaskCommentDTOToCrmTaskCommentAfterMapping(CrmTaskCommentDTO dto, @MappingTarget CrmTaskComment entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}

