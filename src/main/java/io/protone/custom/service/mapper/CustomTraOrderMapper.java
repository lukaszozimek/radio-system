package io.protone.custom.service.mapper;

import io.protone.custom.service.TraCustomerService;
import io.protone.custom.service.dto.CorDictionaryPT;
import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.service.dto.thin.SchLibItemThinPT;
import io.protone.custom.service.dto.thin.TraAdvertisementThinPT;
import io.protone.custom.service.dto.thin.TraCustomerThinPT;
import io.protone.domain.*;
import io.protone.service.dto.TraOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomTraOrderMapper {
    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "status", target = "statusId")
    @Mapping(source = "advertisment", target = "advertismentId")
    @Mapping(source = "campaign.id", target = "campaignId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(target = "emissions", ignore = true)
    TraOrderPT DB2DTO(TraOrder traOrder);

    List<TraOrderPT> DBs2DTOs(List<TraOrder> traOrders);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "advertismentId", target = "advertisment")
    @Mapping(source = "campaignId", target = "campaign")
    @Mapping(target = "emissions", ignore = true)
    TraOrder DTO2DB(TraOrderPT traOrderDTO);

    List<TraOrder> DTOs2DBs(List<TraOrderPT> traOrderDTOs);

    CorDictionary corDictionaryFromCorDictionaryPT(CorDictionaryPT coreUserThinPT);

    CorDictionaryPT corDictionaryPTFromCorDictionary(CorDictionary coreUserThinPT);

    TraAdvertisement traAdvertisementFromTraAdvertisementThinPT(TraAdvertisementThinPT coreUserThinPT);

    TraAdvertisementThinPT traAdvertisementThinPTFromTraAdvertisement(TraAdvertisement coreUserThinPT);

    CrmAccount crmAccountFromTraCustomerThinPT(TraCustomerThinPT coreUserThinPT);

    TraCustomerThinPT traCustomerThinPTFromCrmAccount(CrmAccount coreUserThinPT);

    default TraCampaign traCampaignFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraCampaign traCampaign = new TraCampaign();
        traCampaign.setId(id);
        return traCampaign;
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
