package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TraCampaignDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TraCampaign and its DTO TraCampaignDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraCampaignMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "price.id", target = "priceId")
    @Mapping(source = "orders.id", target = "ordersId")
    TraCampaignDTO traCampaignToTraCampaignDTO(TraCampaign traCampaign);

    List<TraCampaignDTO> traCampaignsToTraCampaignDTOs(List<TraCampaign> traCampaigns);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "priceId", target = "price")
    @Mapping(source = "ordersId", target = "orders")
    @Mapping(target = "emissions", ignore = true)
    TraCampaign traCampaignDTOToTraCampaign(TraCampaignDTO traCampaignDTO);

    List<TraCampaign> traCampaignDTOsToTraCampaigns(List<TraCampaignDTO> traCampaignDTOs);

    default CrmAccount crmAccountFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmAccount crmAccount = new CrmAccount();
        crmAccount.setId(id);
        return crmAccount;
    }

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }

    default TraCampaingStatus traCampaingStatusFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraCampaingStatus traCampaingStatus = new TraCampaingStatus();
        traCampaingStatus.setId(id);
        return traCampaingStatus;
    }

    default TraPrice traPriceFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraPrice traPrice = new TraPrice();
        traPrice.setId(id);
        return traPrice;
    }

    default TraOrder traOrderFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraOrder traOrder = new TraOrder();
        traOrder.setId(id);
        return traOrder;
    }
}
