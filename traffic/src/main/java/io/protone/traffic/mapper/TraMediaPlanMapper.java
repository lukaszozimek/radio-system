package io.protone.traffic.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.mapper.LibFileItemMapper;
import io.protone.traffic.api.dto.TraMediaPlanDTO;
import io.protone.traffic.api.dto.thin.TraMediaPlanThinDTO;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.domain.TraMediaPlanBlock;
import io.protone.traffic.domain.TraMediaPlanEmission;
import io.protone.traffic.domain.TraMediaPlanPlaylistDate;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@Mapper(componentModel = "spring", uses = {TraMediaPlanBlockMapper.class, TraMediaPlanEmissionMapper.class, TraMediaPlanPlaylistDateMapper.class, TraCustomerMapper.class, LibFileItemMapper.class})
public interface TraMediaPlanMapper {

    @Mapping(source = "traMediaPlan.account", target = "traCustomerThinDTO")
    @Mapping(source = "traMediaPlan.libFileItem", target = "libFileItemDTO")
    @Mapping(source = "traMediaPlanPlaylistDates", target = "traMediaPlan.traMediaPlanPlaylistDateDTOS")
    @Mapping(source = "traMediaPlanEmissions", target = "traMediaPlan.traMediaPlanEmissionDTOS")
    @Mapping(source = "traMediaPlanBlockList", target = "traMediaPlan.traMediaPlanBlockDTOS")
    TraMediaPlanDTO DB2DTO(TraMediaPlan traMediaPlan, List<TraMediaPlanBlock> traMediaPlanBlockList, List<TraMediaPlanPlaylistDate> traMediaPlanPlaylistDates, List<TraMediaPlanEmission> traMediaPlanEmissions);

    @Mapping(source = "account", target = "traCustomerThinDTO")
    @Mapping(source = "libFileItem", target = "libFileItemDTO")
    TraMediaPlanDTO DB2DTO(TraMediaPlan traMediaPlan);

    @Mapping(source = "account", target = "traCustomerThinDTO")
    @Mapping(source = "libFileItem", target = "libFileItemDTO")
    TraMediaPlanThinDTO DBThin2DTOThin(TraMediaPlan traMediaPlan);

    List<TraMediaPlanDTO> DBs2DTOs(List<TraMediaPlan> traMediaPlans);

    List<TraMediaPlanThinDTO> DBsThin2DTOsThin(List<TraMediaPlan> traMediaPlans);

    @Mapping(source = "traCustomerThinDTO", target = "account")
    @Mapping(source = "libFileItemDTO", target = "libFileItem")
    TraMediaPlan DTO2DB(TraMediaPlanDTO traMediaPlanDTO, @Context CorChannel corChannel);

    default List<TraMediaPlan> DTOs2DBs(List<TraMediaPlanDTO> traMediaPlanDTOS, @Context CorChannel corChannel) {
        List<TraMediaPlan> traMediaPlans = new ArrayList<>();
        if (traMediaPlanDTOS.isEmpty() || traMediaPlanDTOS == null) {
            return null;
        }
        for (TraMediaPlanDTO dto : traMediaPlanDTOS) {
            traMediaPlans.add(DTO2DB(dto, corChannel));
        }
        return traMediaPlans;
    }

    @AfterMapping
    default void traMediaPlanDTOToTraMediaPlanAfterMapping(TraMediaPlanDTO dto, @MappingTarget TraMediaPlan entity, @Context CorChannel corChannel) {
        entity.setChannel(corChannel);
    }
}
