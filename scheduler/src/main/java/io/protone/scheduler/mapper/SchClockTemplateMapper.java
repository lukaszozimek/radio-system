package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.scheduler.api.dto.SchClockTemplateDTO;
import io.protone.scheduler.api.dto.thin.SchClockTemplateThinDTO;
import io.protone.scheduler.domain.SchClockTemplate;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionTemplateMapper.class, SchEventTemplateMapper.class, CorDictionaryMapper.class, CorUserMapper.class})
public interface SchClockTemplateMapper {

    @Mapping(source = "schEventTemplateDTOS", target = "schEventTemplates")
    SchClockTemplate DTO2DB(SchClockTemplateDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    @Mapping(source = "schEventTemplates", target = "schEventTemplateDTOS")
    SchClockTemplateDTO DB2DTO(SchClockTemplate entity);

    List<SchClockTemplateDTO> DBs2DTOs(List<SchClockTemplate> entityList);

    default List<SchClockTemplate> DTOs2DBs(List<SchClockTemplateDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchClockTemplate> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchClockTemplateDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    List<SchClockTemplateThinDTO> DBs2ThinDTOs(List<SchClockTemplate> schClockList);

    default String map(LibMediaLibrary value) {
        return null;
    }

    @AfterMapping
    default void schClockConfigurationDTOToSchClockConfigurationAfterMapping(SchClockTemplateDTO dto, @MappingTarget SchClockTemplate entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
