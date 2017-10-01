package io.protone.scheduler.mapper;

import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.scheduler.api.dto.SchGridClockConfigurationDTO;
import io.protone.scheduler.domain.SchGridClockConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Grid and its DTO GridDTO.
 */
@Mapper(componentModel = "spring", uses = {SchClockConfigurationMapper.class, CorDictionaryMapper.class, CorUserMapper.class})
public interface SchGridClockConfigurationMapper {
    @Mapping(source = "schClockConfigurationDTO", target = "schClockConfiguration")
    SchGridClockConfiguration DTO2DB(SchGridClockConfigurationDTO dto);

    SchGridClockConfigurationDTO DB2DTO(SchGridClockConfiguration entity);

    List<SchGridClockConfigurationDTO> DBs2DTOs(List<SchGridClockConfiguration> entityList);

    List<SchGridClockConfiguration> DTOs2DBs(List<SchGridClockConfigurationDTO> dList);
}
