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
@Mapper(componentModel = "spring", uses = {SchEmissionTemplateMapper.class, CorDictionaryMapper.class, CorUserMapper.class,})
public interface SchEventTemplateMapper {
    List<SchEventTemplateThinDTO> DBs2ThinDTOs(List<SchEventTemplate> schClockList);

    @Mapping(source = "emissions", target = "emissions")
    SchEventTemplate DTO2DB(SchEventTemplateDTO dto, @Context CorChannel corChannel);

    @Mapping(source = "emissions", target = "emissions")
    SchEventTemplateDTO DB2DTO(SchEventTemplate entity);

    List<SchEventTemplateDTO> DBs2DTOs(List<SchEventTemplate> entityList);

    default List<SchEventTemplate> DTOs2DBs(List<SchEventTemplateDTO> dList, @Context CorChannel corChannel) {
        List<SchEventTemplate> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchEventTemplateDTO dto : dList) {
            eList.add(DTO2DB(dto, corChannel));
        }
        return eList;
    }

    @AfterMapping
    default void schEventConfigurationDTOToSchEventConfigurationAfterMapping(SchEventTemplateDTO dto, @MappingTarget SchEventTemplate entity, @Context CorChannel corChannel) {
        List<SchEventTemplateEvnetTemplate> schEventTemplateEvnetTemplates = Lists.newArrayList();
        entity.sequence(null);
        if (dto.getSchEventTemplateDTOS() != null && !dto.getSchEventTemplateDTOS().isEmpty()) {
            schEventTemplateEvnetTemplates = dto.getSchEventTemplateDTOS().stream().map(schEventTemplateDTO -> new SchEventTemplateEvnetTemplate().child(this.DTO2DB(schEventTemplateDTO, corChannel)).parent(entity).sequence(schEventTemplateDTO.getSequence())).collect(toList());
        }
        entity.getEmissions().stream().forEach(schEmissionTemplate -> schEmissionTemplate.scheventTemplate(entity).channel(corChannel).getAttachments().stream().forEach(schAttachmentTemplate -> schAttachmentTemplate.channel(corChannel).emission(schEmissionTemplate)));
        entity.setSchEventTemplates(schEventTemplateEvnetTemplates);
        entity.setChannel(corChannel);
    }
}
