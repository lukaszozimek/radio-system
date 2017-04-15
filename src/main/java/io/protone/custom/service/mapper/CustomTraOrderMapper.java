package io.protone.custom.service.mapper;

import io.protone.custom.service.TraCustomerService;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTraOrderMapper {

    @Inject
    private CustomSchEmissionMapper schEmissionMapper;

    @Inject
    private CustomCrmAccountMapper customCrmAccountMapper;

    @Inject
    private CustomTRACampaignMapper customTRACampaignMapper;

    @Inject
    private CustomSchEmissionMapper customSchEmissionMapper;
    @Inject
    private CustomTraInvoiceMapper customTraInvoiceMapper;
    @Inject
    private TraCustomerService customerService;

    @Inject
    private CustomTRAAdvertismentMapper traAdvertismentMapper;

    public List<TraOrder> trasnformDTOtoEntity(List<TraOrderPT> traOrderPT, CorNetwork corNetwork) {
        List<TraOrder> traOrdersList = new ArrayList<>();
        traOrderPT.stream().forEach(traOrderPT1 -> {
            traOrdersList.add(trasnformDTOtoEntity(traOrderPT1, corNetwork));
        });
        return traOrdersList;
    }

    public List<TraOrder> trasnformDTOtoEntity(List<TraOrderPT> traOrderPT, Long traCampaign, CorNetwork corNetwork) {
        List<TraOrder> traOrdersList = new ArrayList<>();
        traOrderPT.stream().forEach(traOrderPT1 -> {
            traOrdersList.add(trasnformDTOtoEntity(traOrderPT1, traCampaign, corNetwork));
        });
        return traOrdersList;
    }

    public TraOrder trasnformDTOtoEntity(TraOrderPT traOrderPT, Long traCampaign, CorNetwork corNetwork) {
        if (traOrderPT == null || corNetwork == null) {
            return null;
        }
        TraOrder traOrder = new TraOrder();
        traOrder.setId(traOrderPT.getId());
        traOrder.name(traOrderPT.getName())
            .calculatedPrize(traOrderPT.getCalculatedPrize())
            .startDate(traOrderPT.getStartDate())
            .endDate(traOrderPT.getEndDate());
        traOrder.campaign(new TraCampaign()).getCampaign().setId(traCampaign);
        if (traOrderPT.getTraCampaignPT() != null) {
            traOrder.customer(customCrmAccountMapper.createCrmAcountEntity(traOrderPT.getCustomerPT(), corNetwork));
        }
        if (traOrderPT.getTraInvoice() != null) {
            traOrder.invoice(customTraInvoiceMapper.createEntityFromDTO(traOrderPT.getTraInvoice(), corNetwork));
        }
        if (traOrderPT.getAdvertisment() != null) {
            traOrder.advertisment(traAdvertismentMapper.transformDTOToEntity(traOrderPT.getAdvertisment(), corNetwork));
        }
        traOrder.price(traOrderPT.getTraPrice()).status(traOrder.getStatus())
            .startDate(traOrderPT.getStartDate())
            .endDate(traOrderPT.getEndDate())
            .network(corNetwork);
        return traOrder;
    }

    public TraOrder trasnformDTOtoEntity(TraOrderPT traOrderPT, CorNetwork corNetwork) {
        if (traOrderPT == null || corNetwork == null) {
            return null;
        }
        TraOrder traOrder = new TraOrder();
        traOrder.setId(traOrderPT.getId());
        traOrder.name(traOrderPT.getName())
            .calculatedPrize(traOrderPT.getCalculatedPrize())
            .startDate(traOrderPT.getStartDate())
            .endDate(traOrderPT.getEndDate());
        if (traOrderPT.getTraCampaignPT() != null) {
            traOrder.campaign(customTRACampaignMapper.transfromDTOToEntity(traOrderPT.getTraCampaignPT(), corNetwork));
        }
        if (traOrderPT.getTraCampaignPT() != null) {
            traOrder.customer(customCrmAccountMapper.createCrmAcountEntity(traOrderPT.getCustomerPT(), corNetwork));
        }
        if (traOrderPT.getTraInvoice() != null) {
            traOrder.invoice(customTraInvoiceMapper.createEntityFromDTO(traOrderPT.getTraInvoice(), corNetwork));
        }
        if (traOrderPT.getAdvertisment() != null) {
            traOrder.advertisment(traAdvertismentMapper.transformDTOToEntity(traOrderPT.getAdvertisment(), corNetwork));
        }
        traOrder.price(traOrderPT.getTraPrice()).status(traOrder.getStatus())
            .startDate(traOrderPT.getStartDate())
            .endDate(traOrderPT.getEndDate())
            .network(corNetwork);
        return traOrder;
    }

    public List<TraOrderPT> transfromEntitesToDTO(Set<TraOrder> traOrder) {
        return traOrder.stream().map(this::transfromEntiteToDTO).collect(toList());
    }

    public TraOrderPT transfromEntiteToDTO(TraOrder traOrder) {
        TraOrderPT traOrderPt = new TraOrderPT().id(traOrder.getId()).name(traOrder.getName())
            .calculatedPrize(traOrder.getCalculatedPrize())
            .startDate(traOrder.getStartDate())
            .endDate(traOrder.getEndDate());
        if (traOrder.getCampaign() != null) {
//            traOrderPt.traCampaign(customTRACampaignMapper.transfromEntitytoDTO(traOrder.getCampaign()));
        }
        if (traOrder.getCustomer() != null) {
            traOrderPt.customerId(customCrmAccountMapper.createCustomerTrafficDTO(traOrder.getCustomer()));
        }
        traOrderPt.traPrice(traOrder.getPrice()).traOrderStatus(traOrder.getStatus());
        if (traOrder.getInvoice() != null) {
            traOrderPt.traInvoiceT(customTraInvoiceMapper.createDTOFromEnity(traOrder.getInvoice()));
        }
        if (traOrder.getEmissions() != null) {
            traOrderPt.emissions(customSchEmissionMapper.createDTOFromListEntites(traOrder.getEmissions()));
        }
        if (traOrder.getAdvertisment() != null) {
            traOrderPt.advertimsnet(traAdvertismentMapper.transformEntityToDTO(traOrder.getAdvertisment()));
        }
        return traOrderPt;
    }


}
