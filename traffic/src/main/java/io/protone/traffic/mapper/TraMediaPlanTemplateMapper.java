package io.protone.traffic.mapper;


import io.protone.core.domain.CorNetwork;
import io.protone.traffic.api.dto.TraMediaPlanTemplateDTO;
import io.protone.traffic.domain.TraMediaPlanTemplate;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Mapper(componentModel = "spring")
public interface TraMediaPlanTemplateMapper {

    TraMediaPlanTemplateDTO DB2DTO(TraMediaPlanTemplate traMediaPlanTemplate);

    List<TraMediaPlanTemplateDTO> DBs2DTOs(List<TraMediaPlanTemplate> traMediaPlanTemplates);

    TraMediaPlanTemplate DTO2DB(TraMediaPlanTemplateDTO traMediaPlanTemplateDTO, @Context CorNetwork network);

    default List<TraMediaPlanTemplate> DTOs2DBs(List<TraMediaPlanTemplateDTO> traMediaPlanTemplateDTOS, @Context CorNetwork network) {
        List<TraMediaPlanTemplate> traMediaPlanTemplates = new ArrayList<>();
        if (traMediaPlanTemplateDTOS.isEmpty() || traMediaPlanTemplateDTOS == null) {
            return null;
        }
        for (TraMediaPlanTemplateDTO dto : traMediaPlanTemplateDTOS) {
            traMediaPlanTemplates.add(DTO2DB(dto, network));
        }
        return traMediaPlanTemplates;
    }

    @AfterMapping
    default void traMediaPlanTemplateDTOToTraMediaPlanTemplateAfterMapping(TraMediaPlanTemplateDTO dto, @MappingTarget TraMediaPlanTemplate entity, @Context CorNetwork network) {
        entity.setNetwork(network);

    }
}
