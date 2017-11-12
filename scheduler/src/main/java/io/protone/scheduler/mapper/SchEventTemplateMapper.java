package io.protone.scheduler.mapper;

import com.google.common.collect.Lists;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.scheduler.api.dto.SchEventTemplateDTO;
import io.protone.scheduler.api.dto.thin.SchEventTemplateThinDTO;
import io.protone.scheduler.domain.SchEventTemplate;
import io.protone.scheduler.domain.SchEventTemplateEvnetTemplate;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionTemplateMapper.class, SchAttachmentTemplateMapper.class, CorDictionaryMapper.class, CorUserMapper.class,})
public interface SchEventTemplateMapper {
    List<SchEventTemplateThinDTO> DBs2ThinDTOs(List<SchEventTemplate> schClockList);

    SchEventTemplate DTO2DB(SchEventTemplateDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    @Mapping(source = "schEventTemplates", target = "schEventTemplateDTOS")
    SchEventTemplateDTO DB2DTO(SchEventTemplate entity);

    List<SchEventTemplateDTO> DBs2DTOs(List<SchEventTemplate> entityList);

    default List<SchEventTemplate> DTOs2DBs(List<SchEventTemplateDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchEventTemplate> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchEventTemplateDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    @AfterMapping
    default void schEventConfigurationDTOToSchEventConfigurationAfterMapping(SchEventTemplateDTO dto, @MappingTarget SchEventTemplate entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchEventTemplateEvnetTemplate> schEventTemplateEvnetTemplates = Lists.newArrayList();
        if (dto.getSchEventTemplateDTOS() != null && !dto.getSchEventTemplateDTOS().isEmpty()) {
            schEventTemplateEvnetTemplates = dto.getSchEventTemplateDTOS().stream().map(schEventTemplateDTO -> new SchEventTemplateEvnetTemplate().child(this.DTO2DB(schEventTemplateDTO, network, corChannel)).parent(entity).sequence(schEventTemplateDTO.getSequence())).collect(toList());
        }
        entity.setSchEventTemplates(schEventTemplateEvnetTemplates);
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
