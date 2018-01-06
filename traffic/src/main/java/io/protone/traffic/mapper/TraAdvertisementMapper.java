package io.protone.traffic.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.crm.domain.CrmAccount;
import io.protone.library.mapper.LibMediaItemMapper;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.traffic.api.dto.TraAdvertisementDTO;
import io.protone.traffic.api.dto.thin.TraAdvertisementThinDTO;
import io.protone.traffic.domain.TraAdvertisement;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CorDictionaryMapper.class, LibMediaItemMapper.class, CorUserMapper.class, LibMediaItemThinMapper.class})
public interface TraAdvertisementMapper {
    @Mapping(source = "industry", target = "industryId")
    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "type", target = "typeId")
    @Mapping(source = "libMediaItems", target = "libMediaItemThinDTOList")
    TraAdvertisementDTO DB2DTO(TraAdvertisement traAdvertisement);

    List<TraAdvertisementDTO> DBs2DTOs(List<TraAdvertisement> traAdvertisements);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "industryId", target = "industry")
    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "libMediaItemThinDTOList", target = "libMediaItems")
    TraAdvertisement DTO2DB(TraAdvertisementDTO traAdvertisementDTO, @Context CorChannel corNetwork);

    default List<TraAdvertisement> DTOs2DBs(List<TraAdvertisementDTO> traAdvertisementDTOs, CorChannel networkId) {
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
    default void traAdvertisementPTToTraAdvertisementAfterMapping(TraAdvertisementDTO dto, @MappingTarget TraAdvertisement entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }

    @Mapping(source = "customerId", target = "customer")
    TraAdvertisement traAdvertisementFromTraAdvertisementThinPT(TraAdvertisementThinDTO coreUserThinPT);

    @Mapping(source = "customer", target = "customerId")
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
