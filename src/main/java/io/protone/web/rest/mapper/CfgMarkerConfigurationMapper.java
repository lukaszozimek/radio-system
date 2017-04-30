package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfMarkerConfigurationPT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.CfgMarkerConfiguration;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraOrder;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 16.01.2017.
 */

@Mapper(componentModel = "spring", uses = {})
public interface CfgMarkerConfigurationMapper {

    ConfMarkerConfigurationPT DB2DTO(CfgMarkerConfiguration cFGMarkerConfiguration);

    List<ConfMarkerConfigurationPT> DBs2DTOs(List<CfgMarkerConfiguration> cFGMarkerConfigurations);

    CfgMarkerConfiguration DTO2DB(ConfMarkerConfigurationPT cFGMarkerConfigurationDTO, @Context CorNetwork corNetwork);

    default List<CfgMarkerConfiguration> DTOs2DBs(List<ConfMarkerConfigurationPT> confMarkerConfigurationPTS, CorNetwork networkId) {
        List<CfgMarkerConfiguration> crmLeads = new ArrayList<>();
        if (confMarkerConfigurationPTS.isEmpty() || confMarkerConfigurationPTS == null) {
            return null;
        }
        for (ConfMarkerConfigurationPT dto : confMarkerConfigurationPTS) {
            crmLeads.add(DTO2DB(dto, networkId));
        }
        return crmLeads;
    }

    @AfterMapping
    default void confMarkerConfigurationPTToCfgMarkerConfigurationAfterMapping(ConfMarkerConfigurationPT dto, @MappingTarget CfgMarkerConfiguration entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
