package io.protone.scheduler.mapper;

import com.google.common.collect.Lists;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.scheduler.api.dto.SchClockTemplateDTO;
import io.protone.scheduler.api.dto.SchEventTemplateDTO;
import io.protone.scheduler.api.dto.thin.SchClockTemplateThinDTO;
import io.protone.scheduler.domain.SchClockTemplate;
import io.protone.scheduler.domain.SchEventTemplate;
import io.protone.scheduler.domain.SchEventTemplateEvnetTemplate;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionTemplateMapper.class, CorDictionaryMapper.class, CorUserMapper.class})
public interface SchClockTemplateMapper {


    SchClockTemplate DTO2DB(SchClockTemplateDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchClockTemplateDTO DB2DTO(SchClockTemplate entity);

    List<SchClockTemplateDTO> DBs2DTOs(List<SchClockTemplate> entityList);

    SchEventTemplateDTO DB2DTO(SchEventTemplate entity);

    SchEventTemplate DTO2DB(SchEventTemplateDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

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
    default void schClockTemplateDTOToSchClockTemplateAfterMapping(SchClockTemplateDTO dto, @MappingTarget SchClockTemplate entity, @Context CorNetwork network, @Context CorChannel corChannel) {

        if (dto.getSchEventTemplateDTOS() != null && !dto.getSchEventTemplateDTOS().isEmpty()) {
            entity.setSchEventTemplates(dto.getSchEventTemplateDTOS().stream().map(schEventTemplateDTO -> new SchEventTemplateEvnetTemplate().child(this.DTO2DB(schEventTemplateDTO, network, corChannel)).parent(entity).sequence(schEventTemplateDTO.getSequence())).collect(toList()));
        }
        entity.getEmissions().stream().forEach(schEmissionTemplate -> schEmissionTemplate.scheventTemplate(entity).network(network).channel(corChannel).getAttachments().stream().forEach(schAttachmentTemplate -> schAttachmentTemplate.channel(corChannel).network(network).emission(schEmissionTemplate)));
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }

    @AfterMapping
    default void schEventTemplateDTOToSchEventTemplateAfterMapping(SchEventTemplateDTO dto, @MappingTarget SchEventTemplate entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchEventTemplateEvnetTemplate> schEventTemplateEvnetTemplates = Lists.newArrayList();
        if (dto.getSchEventTemplateDTOS() != null && !dto.getSchEventTemplateDTOS().isEmpty()) {
            schEventTemplateEvnetTemplates = dto.getSchEventTemplateDTOS().stream().map(schEventTemplateDTO -> new SchEventTemplateEvnetTemplate().child(this.DTO2DB(schEventTemplateDTO, network, corChannel)).parent(entity).sequence(schEventTemplateDTO.getSequence())).collect(toList());
        }
        entity.getEmissions().stream().forEach(schEmissionTemplate -> schEmissionTemplate.scheventTemplate(entity).network(network).channel(corChannel).getAttachments().stream().forEach(schAttachmentTemplate -> schAttachmentTemplate.channel(corChannel).network(network).emission(schEmissionTemplate)));

        entity.setSchEventTemplates(schEventTemplateEvnetTemplates);
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }

    @AfterMapping
    default void schClockTemplateToSchClockTemplateDTOAfterMapping(SchClockTemplate entity, @MappingTarget SchClockTemplateDTO dto) {
        List<SchEventTemplateDTO> schEventTemplateEvnetTemplates = Lists.newArrayList();
        if (entity.getSchEventTemplates() != null && !entity.getSchEventTemplates().isEmpty()) {
            schEventTemplateEvnetTemplates = entity.getSchEventTemplates().stream()
                    .map(eventTemplateEvnetTemplateRFunction -> this.DB2DTO(eventTemplateEvnetTemplateRFunction.getChild()).sequence(eventTemplateEvnetTemplateRFunction.getSequence())).collect(toList());
        }
        dto.setSchEventTemplateDTOS(schEventTemplateEvnetTemplates);
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
