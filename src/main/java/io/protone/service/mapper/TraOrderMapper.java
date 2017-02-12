package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TraOrderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TraOrder and its DTO TraOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraOrderMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "campaign.id", target = "campaignId")
    @Mapping(source = "price.id", target = "priceId")
    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    TraOrderDTO traOrderToTraOrderDTO(TraOrder traOrder);

    List<TraOrderDTO> traOrdersToTraOrderDTOs(List<TraOrder> traOrders);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "campaignId", target = "campaign")
    @Mapping(source = "priceId", target = "price")
    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "invoiceId", target = "invoice")
    TraOrder traOrderDTOToTraOrder(TraOrderDTO traOrderDTO);

    List<TraOrder> traOrderDTOsToTraOrders(List<TraOrderDTO> traOrderDTOs);

    default CrmAccount crmAccountFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmAccount crmAccount = new CrmAccount();
        crmAccount.setId(id);
        return crmAccount;
    }

    default TraCampaign traCampaignFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraCampaign traCampaign = new TraCampaign();
        traCampaign.setId(id);
        return traCampaign;
    }

    default TraPrice traPriceFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraPrice traPrice = new TraPrice();
        traPrice.setId(id);
        return traPrice;
    }

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }

    default TraOrderStatus traOrderStatusFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraOrderStatus traOrderStatus = new TraOrderStatus();
        traOrderStatus.setId(id);
        return traOrderStatus;
    }

    default TraInvoice traInvoiceFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraInvoice traInvoice = new TraInvoice();
        traInvoice.setId(id);
        return traInvoice;
    }
}
