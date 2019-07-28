package io.protone.scheduler.mapper;

import com.google.common.collect.Lists;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.api.dto.thin.SchClockThinDTO;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchBlockSchBlock;
import io.protone.scheduler.domain.SchClock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mapper for the entity Clock and its DTO ClockDTO.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionMapper.class, CorDictionaryMapper.class})
public interface SchClockMapper {
    SchClock DTO2DB(SchClockDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchClockDTO DB2DTO(SchClock entity);

    List<SchClockDTO> DBs2DTOs(List<SchClock> entityList);

    SchBlock DTO2DB(SchBlockDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchBlockDTO DB2DTO(SchBlock entity);

    default List<SchClock> DTOs2DBs(List<SchClockDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchClock> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchClockDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    List<SchClockThinDTO> DBs2ThinDTOs(List<SchClock> schClockList);

    default String map(LibMediaLibrary value) {
        return null;
    }

    @AfterMapping
    default void schClockDTOToSchClockAfterMapping(SchClockDTO dto, @MappingTarget SchClock entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }

    @AfterMapping
    default void schClockTemplateDTOToSchClockAfterMapping(SchClockDTO dto, @MappingTarget SchClock entity, @Context CorNetwork network, @Context CorChannel corChannel) {

        if (dto.getBlocks() != null && !dto.getBlocks().isEmpty()) {
            entity.setBlocks(dto.getBlocks().stream().map(schBlockDTO -> new SchBlockSchBlock().child(this.DTO2DB(schBlockDTO, network, corChannel)).parent(entity).sequence(schBlockDTO.getSequence())).collect(toList()));
        }
        entity.getEmissions().stream().forEach(schEmissionTemplate -> schEmissionTemplate.block(entity).network(network).channel(corChannel).getAttachments().stream().forEach(schAttachmentTemplate -> schAttachmentTemplate.channel(corChannel).network(network).emission(schEmissionTemplate)));
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }

    @AfterMapping
    default void schEventTemplateDTOToSchEventTemplateAfterMapping(SchBlockDTO dto, @MappingTarget SchBlock entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchBlockSchBlock> schEventTemplateEvnetTemplates = Lists.newArrayList();
        if (dto.getBlocks() != null && !dto.getBlocks().isEmpty()) {
            schEventTemplateEvnetTemplates = dto.getBlocks().stream().map(schEventTemplateDTO -> new SchBlockSchBlock().child(this.DTO2DB(schEventTemplateDTO, network, corChannel)).parent(entity).sequence(schEventTemplateDTO.getSequence())).collect(toList());
        }
        entity.getEmissions().stream().forEach(schEmissionTemplate -> schEmissionTemplate.block(entity).network(network).channel(corChannel).getAttachments().stream().forEach(schAttachmentTemplate -> schAttachmentTemplate.channel(corChannel).network(network).emission(schEmissionTemplate)));

        entity.setBlocks(schEventTemplateEvnetTemplates);
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }

    @AfterMapping
    default void schClockTemplateToSchClockDTOAfterMapping(SchClock entity, @MappingTarget SchClockDTO dto) {
        List<SchBlockDTO> schEventTemplateEvnetTemplates = Lists.newArrayList();
        if (entity.getBlocks() != null && !entity.getBlocks().isEmpty()) {
            schEventTemplateEvnetTemplates = entity.getBlocks().stream()
                    .map(blocBlockEntity -> this.DB2DTO(blocBlockEntity.getChild()).sequence(blocBlockEntity.getSequence())).collect(toList());
        }
        dto.setBlocks(schEventTemplateEvnetTemplates);
    }

    @AfterMapping
    default void schEventTemplateToSchEventConfigurationDTOAfterMapping(SchClock entity, @MappingTarget SchClockDTO dto) {
        List<SchBlockDTO> schBlockDTOS = Lists.newArrayList();
        if (entity.getBlocks() != null && !entity.getBlocks().isEmpty()) {
            schBlockDTOS = entity.getBlocks().stream()
                    .map(eventTemplateEvnetTemplateRFunction -> this.DB2DTO(eventTemplateEvnetTemplateRFunction.getChild()).sequence(eventTemplateEvnetTemplateRFunction.getSequence())).collect(toList());
        }
        dto.setBlocks(schBlockDTOS);
    }
}
