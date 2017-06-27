package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.SchEventPT;
import io.protone.domain.SchTemplate;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity SchTemplate and its DTO SchTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomSchTemplateMapper {

    SchEventPT DBToDTO(SchTemplate schTemplate);

    List<SchEventPT> DBsToDTOs(List<SchTemplate> schTemplates);

    SchTemplate DTOToDB(SchEventPT schTemplateDTO);

    List<SchTemplate> DTOsToDBs(List<SchEventPT> schTemplateDTOs);


}
