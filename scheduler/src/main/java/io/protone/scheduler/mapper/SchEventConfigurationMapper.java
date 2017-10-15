package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.scheduler.api.dto.SchEventConfigurationDTO;
import io.protone.scheduler.api.dto.thin.SchEventConfigurationThinDTO;
import io.protone.scheduler.domain.SchEventConfiguration;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionConfigurationMapper.class, SchAttachmentConfigurationMapper.class, CorDictionaryMapper.class, CorUserMapper.class, SchEventMapper.class})
public interface SchEventConfigurationMapper {
    List<SchEventConfigurationThinDTO> DBs2ThinDTOs(List<SchEventConfiguration> schClockList);

    @Mapping(source = "schEventConfigurations", target = "schEventConfigurations")
    @Mapping(source = "events", target = "schEvents")
    SchEventConfiguration DTO2DB(SchEventConfigurationDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    @Mapping(source = "schEventConfigurations", target = "schEventConfigurations")
    @Mapping(source = "schEvents", target = "events")
    SchEventConfigurationDTO DB2DTO(SchEventConfiguration entity);

    List<SchEventConfigurationDTO> DBs2DTOs(List<SchEventConfiguration> entityList);

    default List<SchEventConfiguration> DTOs2DBs(List<SchEventConfigurationDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchEventConfiguration> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchEventConfigurationDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    @AfterMapping
    default void schEventConfigurationDTOToSchEventConfigurationAfterMapping(SchEventConfigurationDTO dto, @MappingTarget SchEventConfiguration entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
