package io.protone.scheduler.mapper;

import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.scheduler.api.dto.SchGridClockConfigurationDTO;
import io.protone.scheduler.domain.SchGridClockTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Grid and its DTO GridDTO.
 */
@Mapper(componentModel = "spring", uses = {SchClockTemplateMapper.class, CorDictionaryMapper.class, CorUserMapper.class})
public interface SchGridClockConfigurationMapper {
    @Mapping(source = "schClockTemplateDTO", target = "schClockTemplate")
    SchGridClockTemplate DTO2DB(SchGridClockConfigurationDTO dto);

    @Mapping(source = "schClockTemplate", target = "schClockTemplateDTO")
    SchGridClockConfigurationDTO DB2DTO(SchGridClockTemplate entity);

    List<SchGridClockConfigurationDTO> DBs2DTOs(List<SchGridClockTemplate> entityList);

    List<SchGridClockTemplate> DTOs2DBs(List<SchGridClockConfigurationDTO> dList);
}
