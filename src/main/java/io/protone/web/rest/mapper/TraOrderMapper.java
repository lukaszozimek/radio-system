package io.protone.web.rest.mapper;

import io.protone.web.rest.dto.traffic.TraOrderDTO;
import io.protone.domain.*;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CorDictionaryMapper.class, TraAdvertisementMapper.class, CrmAccountMapper.class})
public interface TraOrderMapper {
    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "status", target = "statusId")
    @Mapping(source = "advertisment", target = "advertismentId")
    @Mapping(source = "campaign.id", target = "campaignId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(target = "emissions", ignore = true)
    TraOrderDTO DB2DTO(TraOrder traOrder);

    List<TraOrderDTO> DBs2DTOs(List<TraOrder> traOrders);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "advertismentId", target = "advertisment")
    @Mapping(source = "invoiceId", target = "invoice")
    @Mapping(source = "campaignId", target = "campaign")
    @Mapping(target = "emissions", ignore = true)
    TraOrder DTO2DB(TraOrderDTO traOrderDTO, @Context CorNetwork corNetwork);

    default List<TraOrder> DTOs2DBs(List<TraOrderDTO> traOrderDTOS, CorNetwork networkId) {
        List<TraOrder> crmLeads = new ArrayList<>();
        if (traOrderDTOS.isEmpty() || traOrderDTOS == null) {
            return null;
        }
        for (TraOrderDTO dto : traOrderDTOS) {
            crmLeads.add(DTO2DB(dto, networkId));
        }
        return crmLeads;
    }

    @AfterMapping
    default void traOrderPTToTraOrderAfterMapping(TraOrderDTO dto, @MappingTarget TraOrder entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

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
