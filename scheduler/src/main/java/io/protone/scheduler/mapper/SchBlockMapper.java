package io.protone.scheduler.mapper;

import com.google.common.collect.Lists;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchBlockSchBlock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mapper for the entity Block and its DTO BlockDTO.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionMapper.class,})
public interface SchBlockMapper {
    SchBlock DTO2DB(SchBlockDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchBlockDTO DB2DTO(SchBlock entity);

    List<SchBlockDTO> DBs2DTOs(List<SchBlock> entityList);

    default List<SchBlock> DTOs2DBs(List<SchBlockDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchBlock> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchBlockDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    @AfterMapping
    default void schBlockDTOToSchBlockAfterMapping(SchBlockDTO dto, @MappingTarget SchBlock entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchBlockSchBlock> schBlocks = Lists.newArrayList();
        entity.sequence(null);
        if (dto.getBlocks() != null && !dto.getBlocks().isEmpty()) {
            schBlocks = dto.getBlocks().stream().map(schBlockDTO -> new SchBlockSchBlock().child(this.DTO2DB(schBlockDTO, network, corChannel)).parent(entity).sequence(schBlockDTO.getSequence())).collect(toList());
        }
        if (entity.getEmissions() != null && !entity.getEmissions().isEmpty()) {
            entity.getEmissions().stream().forEach(schEmissionTemplate -> schEmissionTemplate.block(entity).network(network).channel(corChannel).getAttachments().stream().forEach(schAttachmentTemplate -> schAttachmentTemplate.channel(corChannel).network(network).emission(schEmissionTemplate)));

        }
        entity.setBlocks(schBlocks);
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
