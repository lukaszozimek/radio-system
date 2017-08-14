package io.protone.traffic.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.traffic.api.dto.TraMediaPlanDTO;
import io.protone.traffic.api.dto.thin.TraMediaPlanThinDTO;
import io.protone.traffic.domain.TraMediaPlan;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@Mapper(componentModel = "spring", uses = {TraMediaPlanMapperPlaylist.class, TraCustomerMapper.class, LibMediaItemThinMapper.class})
public interface TraMediaPlanMapper {

    @Mapping(source = "account", target = "traCustomerThinDTO")
    @Mapping(source = "playlists", target = "mediaPlanPlaylistDTOS")
    @Mapping(source = "mediaItem", target = "mediaItemId")
    TraMediaPlanDTO DB2DTO(TraMediaPlan traMediaPlan);

    @Mapping(source = "account", target = "traCustomerThinDTO")
    @Mapping(source = "mediaItem", target = "mediaItemId")
    TraMediaPlanThinDTO DBThin2DTOThin(TraMediaPlan traMediaPlan);

    List<TraMediaPlanDTO> DBs2DTOs(List<TraMediaPlan> traMediaPlans);

    List<TraMediaPlanThinDTO> DBsThin2DTOsThin(List<TraMediaPlan> traMediaPlans);

    @Mapping(source = "traCustomerThinDTO", target = "account")
    @Mapping(source = "mediaPlanPlaylistDTOS", target = "playlists")
    @Mapping(source = "mediaItemId", target = "mediaItem")
    TraMediaPlan DTO2DB(TraMediaPlanDTO traMediaPlanDTO, @Context CorNetwork network, @Context CorChannel corChannel);

    default List<TraMediaPlan> DTOs2DBs(List<TraMediaPlanDTO> traMediaPlanDTOS, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<TraMediaPlan> traMediaPlans = new ArrayList<>();
        if (traMediaPlanDTOS.isEmpty() || traMediaPlanDTOS == null) {
            return null;
        }
        for (TraMediaPlanDTO dto : traMediaPlanDTOS) {
            traMediaPlans.add(DTO2DB(dto, network, corChannel));
        }
        return traMediaPlans;
    }

    @AfterMapping
    default void traMediaPlanDTOToTraMediaPlanAfterMapping(TraMediaPlanDTO dto, @MappingTarget TraMediaPlan entity, @Context CorNetwork corNetwork, @Context CorChannel corChannel) {
        entity.setNetwork(corNetwork);
        entity.setChannel(corChannel);
    }
}
