package io.protone.scheduler.mapper;

import com.google.common.collect.Lists;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.scheduler.api.dto.SchClockTemplateDTO;
import io.protone.scheduler.api.dto.SchEventTemplateDTO;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.api.dto.thin.SchGridThinDTO;
import io.protone.scheduler.domain.SchClockTemplate;
import io.protone.scheduler.domain.SchEventTemplate;
import io.protone.scheduler.domain.SchEventTemplateEvnetTemplate;
import io.protone.scheduler.domain.SchGrid;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mapper for the entity Grid and its DTO GridDTO.
 */
@Mapper(componentModel = "spring", uses = {CorDictionaryMapper.class, SchEmissionTemplateMapper.class, CorUserMapper.class})
public interface SchGridMapper {
    @Mapping(source = "defaultGrid", target = "defaultGrid")
    SchGrid DTO2DB(SchGridDTO dto, @Context CorChannel corChannel);

    @Mapping(source = "defaultGrid", target = "defaultGrid")
    SchGridDTO DB2DTO(SchGrid entity);

    List<SchGridDTO> DBs2DTOs(List<SchGrid> entityList);

    default List<SchGrid> DTOs2DBs(List<SchGridDTO> dList, @Context CorChannel corChannel) {
        List<SchGrid> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchGridDTO dto : dList) {
            eList.add(DTO2DB(dto, corChannel));
        }
        return eList;
    }

    List<SchGridThinDTO> DBs2ThinDTOs(List<SchGrid> schClockList);

    @Mapping(source = "defaultGrid", target = "defaultGrid")
    SchGridThinDTO DB2ThinDTO(SchGrid schClockList);

    SchEventTemplateDTO DB2DTO(SchEventTemplate entity);

    SchClockTemplateDTO DB2DTO(SchClockTemplate entity);

    default String map(LibMediaLibrary value) {
        return null;
    }

    @AfterMapping
    default void schGridDTOToSchGridnAfterMapping(SchGridDTO dto, @MappingTarget SchGrid entity, @Context CorChannel corChannel) {
        if (dto.getClocks() != null && !dto.getClocks().isEmpty()) {
            entity.schEventTemplates(dto.getClocks().stream().map(schClockTemplateDTO -> {
                return new SchEventTemplateEvnetTemplate().parent(entity).sequence(schClockTemplateDTO.getSequence()).child(new SchClockTemplate().id(schClockTemplateDTO.getId()));
            }).collect(toList()));
        }
        entity.setChannel(corChannel);
    }

    @AfterMapping
    default void schGridToSchGridDTOAfterMapping(SchGrid entity, @MappingTarget SchGridDTO dto) {
        if (entity.getSchEventTemplates() != null && !entity.getSchEventTemplates().isEmpty()) {
            dto.setClocks(entity.getSchEventTemplates().stream().map(schEventTemplateEvnetTemplate -> this.DB2DTO((SchClockTemplate) schEventTemplateEvnetTemplate.getChild()).sequence(schEventTemplateEvnetTemplate.getSequence())).collect(toList()));
        }

    }


    @AfterMapping
    default SchClockTemplateDTO schClockTemplateToSchClockTemplateDTOAfterMapping(SchClockTemplate entity, @MappingTarget SchClockTemplateDTO dto) {
        List<SchEventTemplateDTO> schEventTemplateEvnetTemplates = Lists.newArrayList();
        if (entity.getSchEventTemplates() != null && !entity.getSchEventTemplates().isEmpty()) {
            schEventTemplateEvnetTemplates = entity.getSchEventTemplates().stream()
                    .map(eventTemplateEvnetTemplateRFunction -> this.DB2DTO(eventTemplateEvnetTemplateRFunction.getChild()).sequence(eventTemplateEvnetTemplateRFunction.getSequence())).collect(toList());
        }
        dto.setSchEventTemplateDTOS(schEventTemplateEvnetTemplates);
        return dto;
    }

    @AfterMapping
    default void schEventTemplateToSchEventConfigurationDTOAfterMapping(SchEventTemplate entity, @MappingTarget SchEventTemplateDTO dto) {
        List<SchEventTemplateDTO> schEventTemplateEvnetTemplates = Lists.newArrayList();
        if (entity.getSchEventTemplates() != null && !entity.getSchEventTemplates().isEmpty()) {
            schEventTemplateEvnetTemplates = entity.getSchEventTemplates().stream()
                    .map(eventTemplateEvnetTemplateRFunction -> this.DB2DTO(eventTemplateEvnetTemplateRFunction.getChild()).sequence(eventTemplateEvnetTemplateRFunction.getSequence())).collect(toList());
        }
        dto.setSchEventTemplateDTOS(schEventTemplateEvnetTemplates);
    }
}
