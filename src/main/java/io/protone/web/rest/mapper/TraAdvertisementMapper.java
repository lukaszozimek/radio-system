package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.web.rest.dto.thin.TraAdvertisementThinDTO;
import io.protone.domain.*;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CorDictionaryMapper.class, LibItemMapper.class})
public interface TraAdvertisementMapper {
    @Mapping(source = "mediaItem", target = "mediaItemId")
    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "type", target = "typeId")
    TraAdvertisementPT DB2DTO(TraAdvertisement traAdvertisement);

    List<TraAdvertisementPT> DBs2DTOs(List<TraAdvertisement> traAdvertisements);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "industryId", target = "industry")
    @Mapping(source = "typeId", target = "type")
    TraAdvertisement DTO2DB(TraAdvertisementPT traAdvertisementDTO, @Context CorNetwork corNetwork);

    default List<TraAdvertisement> DTOs2DBs(List<TraAdvertisementPT> traAdvertisementDTOs, CorNetwork networkId) {
        List<TraAdvertisement> traCampaigns = new ArrayList<>();
        if (traAdvertisementDTOs.isEmpty() || traAdvertisementDTOs == null) {
            return null;
        }
        for (TraAdvertisementPT dto : traAdvertisementDTOs) {
            traCampaigns.add(DTO2DB(dto, networkId));
        }
        return traCampaigns;
    }

    @AfterMapping
    default void traAdvertisementPTToTraAdvertisementAfterMapping(TraAdvertisementPT dto, @MappingTarget TraAdvertisement entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

    TraAdvertisement traAdvertisementFromTraAdvertisementThinPT(TraAdvertisementThinDTO coreUserThinPT);

    TraAdvertisementThinDTO traAdvertisementThinPTFromTraAdvertisement(TraAdvertisement coreUserThinPT);


}
