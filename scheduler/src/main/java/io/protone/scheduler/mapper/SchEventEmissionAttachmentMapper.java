package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.scheduler.api.dto.SchEventEmissionAttachmentDTO;
import io.protone.scheduler.domain.SchEventEmissionAttachment;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Attachment and its SchAttachmentDTOTO AttachmentSchAttachmentDTOTO.
 */
@Mapper(componentModel = "spring", uses = {LibMediaItemThinMapper.class, SchEmissionConfigurationMapper.class, SchConfigurationTimeParamsMapper.class})
public interface SchEventEmissionAttachmentMapper {
    SchEventEmissionAttachment DTO2DB(SchEventEmissionAttachmentDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchEventEmissionAttachmentDTO DB2DTO(SchEventEmissionAttachment entity);

    List<SchEventEmissionAttachmentDTO> DBs2DTOs(List<SchEventEmissionAttachment> entityList);

    default List<SchEventEmissionAttachment> DTOs2DBs(List<SchEventEmissionAttachmentDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchEventEmissionAttachment> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchEventEmissionAttachmentDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    @AfterMapping
    default void schEventEmissionAttachmentDTOToSchEventEmissionAttachmentAfterMapping(SchEventEmissionAttachmentDTO dto, @MappingTarget SchEventEmissionAttachment entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
