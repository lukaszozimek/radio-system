package io.protone.application.web.rest.mapper;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraAdvertisement;
import io.protone.web.rest.dto.traffic.TraAdvertisementDTO;
import io.protone.web.rest.dto.traffic.thin.TraAdvertisementThinDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CorDictionaryMapper.class, LibItemMapper.class})
public interface TraAdvertisementMapper {
    @Mapping(source = "mediaItem", target = "mediaItemId")
    @Mapping(source = "industry", target = "industryId")
    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "type", target = "typeId")
    TraAdvertisementDTO DB2DTO(TraAdvertisement traAdvertisement);

    List<TraAdvertisementDTO> DBs2DTOs(List<TraAdvertisement> traAdvertisements);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "industryId", target = "industry")
    @Mapping(source = "typeId", target = "type")
    TraAdvertisement DTO2DB(TraAdvertisementDTO traAdvertisementDTO, @Context CorNetwork corNetwork);

    default List<TraAdvertisement> DTOs2DBs(List<TraAdvertisementDTO> traAdvertisementDTOs, CorNetwork networkId) {
        List<TraAdvertisement> traCampaigns = new ArrayList<>();
        if (traAdvertisementDTOs.isEmpty() || traAdvertisementDTOs == null) {
            return null;
        }
        for (TraAdvertisementDTO dto : traAdvertisementDTOs) {
            traCampaigns.add(DTO2DB(dto, networkId));
        }
        return traCampaigns;
    }

    @AfterMapping
    default void traAdvertisementPTToTraAdvertisementAfterMapping(TraAdvertisementDTO dto, @MappingTarget TraAdvertisement entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "mediaItemId", target = "mediaItem")
    TraAdvertisement traAdvertisementFromTraAdvertisementThinPT(TraAdvertisementThinDTO coreUserThinPT);

    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "mediaItem", target = "mediaItemId")
    TraAdvertisementThinDTO traAdvertisementThinPTFromTraAdvertisement(TraAdvertisement coreUserThinPT);

    default CrmAccount crmAccountFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmAccount crmAccount = new CrmAccount();
        crmAccount.setId(id);
        return crmAccount;
    }

    default Long idFromCrmAccount(CrmAccount account) {
        if (account == null) {
            return null;
        }
        return account.getId();
    }
}
