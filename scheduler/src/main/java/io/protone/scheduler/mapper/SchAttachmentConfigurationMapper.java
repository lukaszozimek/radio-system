package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.scheduler.api.dto.SchAttachmentConfigurationDTO;
import io.protone.scheduler.domain.SchAttachmentConfiguration;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Attachment and its SchAttachmentDTOTO AttachmentSchAttachmentDTOTO.
 */
@Mapper(componentModel = "spring", uses = {LibMediaItemThinMapper.class, SchEmissionConfigurationMapper.class})
public interface SchAttachmentConfigurationMapper {
    SchAttachmentConfiguration DTO2DB(SchAttachmentConfigurationDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchAttachmentConfigurationDTO DB2DTO(SchAttachmentConfiguration entity);

    List<SchAttachmentConfigurationDTO> DBs2DTOs(List<SchAttachmentConfiguration> entityList);

    default List<SchAttachmentConfiguration> DTOs2DBs(List<SchAttachmentConfigurationDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchAttachmentConfiguration> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchAttachmentConfigurationDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    @AfterMapping
    default void schAttachmentConfigurationDTOToSchAttachmentConfigurationAfterMapping(SchAttachmentConfigurationDTO dto, @MappingTarget SchAttachmentConfiguration entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
