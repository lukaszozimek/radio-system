package io.protone.web.rest.mapper;

import io.protone.web.rest.dto.library.LibMarkerDTO;
import io.protone.web.rest.dto.traffic.TraCampaignDTO;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibMarkerTypeEnum;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {TraOrderMapper.class, CorDictionaryMapper.class})
public interface TraCampaignMapper {

    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "orders", target = "orders")
    TraCampaignDTO DB2DTO(TraCampaign traCampaign);

    List<TraCampaignDTO> DBs2DTOs(List<TraCampaign> traCampaigns);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "orders", target = "orders")
    TraCampaign DTO2DB(TraCampaignDTO traCampaignDTO, @Context CorNetwork corNetwork);

    default List<TraCampaign> DTOs2DBs(List<TraCampaignDTO> traCampaignDTO, CorNetwork networkId) {
        List<TraCampaign> traCampaigns = new ArrayList<>();
        if (traCampaignDTO.isEmpty() || traCampaignDTO == null) {
            return null;
        }
        for (TraCampaignDTO dto : traCampaignDTO) {
            traCampaigns.add(DTO2DB(dto, networkId));
        }
        return traCampaigns;
    }

    @AfterMapping
    default void traCampaignPTToTraCampaignAfterMapping(TraCampaignDTO dto, @MappingTarget TraCampaign entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
