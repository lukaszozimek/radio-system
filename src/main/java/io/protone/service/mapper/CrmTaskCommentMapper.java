package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CrmTaskCommentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CrmTaskComment and its DTO CrmTaskCommentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmTaskCommentMapper {

    @Mapping(source = "taskComment.id", target = "taskCommentId")
    @Mapping(source = "createdBy.id", target = "createdById")
    CrmTaskCommentDTO crmTaskCommentToCrmTaskCommentDTO(CrmTaskComment crmTaskComment);

    List<CrmTaskCommentDTO> crmTaskCommentsToCrmTaskCommentDTOs(List<CrmTaskComment> crmTaskComments);

    @Mapping(source = "taskCommentId", target = "taskComment")
    @Mapping(source = "createdById", target = "createdBy")
    CrmTaskComment crmTaskCommentDTOToCrmTaskComment(CrmTaskCommentDTO crmTaskCommentDTO);

    List<CrmTaskComment> crmTaskCommentDTOsToCrmTaskComments(List<CrmTaskCommentDTO> crmTaskCommentDTOs);

    default CrmTask crmTaskFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmTask crmTask = new CrmTask();
        crmTask.setId(id);
        return crmTask;
    }

    default CorUser corUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorUser corUser = new CorUser();
        corUser.setId(id);
        return corUser;
    }
}
