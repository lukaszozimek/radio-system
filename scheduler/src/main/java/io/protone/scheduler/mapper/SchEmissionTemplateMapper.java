package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.scheduler.api.dto.SchEmissionTemplateDTO;
import io.protone.scheduler.domain.SchEmissionTemplate;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Emission and its DTO EmissionDTO.
 */
@Mapper(componentModel = "spring", uses = {LibMediaItemThinMapper.class, SchAttachmentTemplateMapper.class})
public interface SchEmissionTemplateMapper {
    SchEmissionTemplate DTO2DB(SchEmissionTemplateDTO dto, @Context CorChannel corChannel);

    SchEmissionTemplateDTO DB2DTO(SchEmissionTemplate entity);

    List<SchEmissionTemplateDTO> DBs2DTOs(List<SchEmissionTemplate> entityList);

    default List<SchEmissionTemplate> DTOs2DBs(List<SchEmissionTemplateDTO> dList, @Context CorChannel corChannel) {
        List<SchEmissionTemplate> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchEmissionTemplateDTO dto : dList) {
            eList.add(DTO2DB(dto, corChannel));
        }
        return eList;
    }

    default String map(LibMediaLibrary value) {
        return null;
    }

    @AfterMapping
    default void schEmissionConfigurationDTOToSchEmissionConfigurationAfterMapping(SchEmissionTemplateDTO dto, @MappingTarget SchEmissionTemplate entity, @Context CorChannel corChannel) {
        entity.setChannel(corChannel);
    }
}
