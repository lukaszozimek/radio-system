package io.protone.scheduler.mapper;

import com.google.common.collect.Lists;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.api.dto.thin.SchScheduleThinDTO;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchBlockSchBlock;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchSchedule;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mapper for the entity Schedule and its DTO ScheduleDTO.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionMapper.class, CorDictionaryMapper.class})
public interface SchScheduleMapper {
    SchSchedule DTO2DB(SchScheduleDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchScheduleDTO DB2DTO(SchSchedule entity);

    List<SchScheduleDTO> DBs2DTOs(List<SchSchedule> entityList);

    default List<SchSchedule> DTOs2DBs(List<SchScheduleDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchSchedule> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchScheduleDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    SchClockDTO DB2DTO(SchClock entity);

    SchClock DTO2DB(SchClockDTO entity, @Context CorNetwork network, @Context CorChannel corChannel);

    List<SchScheduleThinDTO> DBs2ThinDTOs(List<SchSchedule> schClockList);

    SchBlock DTO2DB(SchBlockDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchBlockDTO DB2DTO(SchBlock entity);

    default String map(LibMediaLibrary value) {
        return null;
    }

    @AfterMapping
    default void schScheduleDTOToSchSchedulenAfterMapping(SchScheduleDTO dto, @MappingTarget SchSchedule entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
        entity.setBlocks(dto.getSchClockDTOS().stream().map(schClockDTO -> new SchBlockSchBlock().parent(entity).child(this.DTO2DB(schClockDTO, network, corChannel)).sequence(schClockDTO.getSequence())).collect(toList()));
    }


    @AfterMapping
    default void schScheduleToSchScheduleDTOAfterMapping(@MappingTarget SchScheduleDTO dto, SchSchedule entity) {
        List<SchClockDTO> schClockDTOS = Lists.newArrayList();
        if (entity.getBlocks() != null && !entity.getBlocks().isEmpty()) {
            schClockDTOS = entity.getBlocks().stream()
                    .map(eventTemplateEvnetTemplateRFunction -> this.DB2DTO((SchClock) eventTemplateEvnetTemplateRFunction.getChild()).sequence(eventTemplateEvnetTemplateRFunction.getSequence())).collect(toList());
        }
        dto.setSchClockDTOS(schClockDTOS);
    }

    @AfterMapping
    default void schClockDTOToSchClockAfterMapping(SchClockDTO dto, @MappingTarget SchClock entity, @Context CorNetwork network, @Context CorChannel corChannel) {

        if (dto.getBlocks() != null && !dto.getBlocks().isEmpty()) {
            entity.setBlocks(dto.getBlocks().stream().map(schBlockDTO -> new SchBlockSchBlock().child(this.DTO2DB(schBlockDTO, network, corChannel)).parent(entity).sequence(schBlockDTO.getSequence())).collect(toList()));
        }
        entity.getEmissions().stream().forEach(schEmissionTemplate -> schEmissionTemplate.block(entity).network(network).channel(corChannel).getAttachments().stream().forEach(schAttachmentTemplate -> schAttachmentTemplate.channel(corChannel).network(network).emission(schEmissionTemplate)));
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }

    @AfterMapping
    default void schBlockDTOToSchBlockAfterMapping(SchBlockDTO dto, @MappingTarget SchBlock entity, @Context CorNetwork network, @Context CorChannel corChannel) {
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
    default void schClockToSchClockDTOAfterMapping(SchClock entity, @MappingTarget SchClockDTO dto) {
        List<SchBlockDTO> schEventTemplateEvnetTemplates = Lists.newArrayList();
        if (entity.getBlocks() != null && !entity.getBlocks().isEmpty()) {
            schEventTemplateEvnetTemplates = entity.getBlocks().stream()
                    .map(blocBlockEntity -> this.DB2DTO(blocBlockEntity.getChild()).sequence(blocBlockEntity.getSequence())).collect(toList());
        }
        dto.setBlocks(schEventTemplateEvnetTemplates);
    }

    @AfterMapping
    default void schBlockToSchBlockDTOAfterMapping(SchBlock entity, @MappingTarget SchBlockDTO dto) {
        List<SchBlockDTO> schBlockDTOS = Lists.newArrayList();
        if (entity.getBlocks() != null && !entity.getBlocks().isEmpty()) {
            schBlockDTOS = entity.getBlocks().stream()
                    .map(eventTemplateEvnetTemplateRFunction -> this.DB2DTO(eventTemplateEvnetTemplateRFunction.getChild()).sequence(eventTemplateEvnetTemplateRFunction.getSequence())).collect(toList());
        }
        dto.setBlocks(schBlockDTOS);
    }

}
