package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomCRMAccountMapper {
    @Inject
    private CustomTRAPersonMapper customTRAPersonMapper;

    @Inject
    private CustomCORAddressMapper corAddressMapper;

    @Inject
    private CustomTRAIndustryMapper industryMapper;

    @Inject
    private CustomCORRangeMapper customCORRangeMapper;

    @Inject
    private CustomCORAreaMapper customCORAreaMapper;

    @Inject
    private CustomCORSizeMapper customCORSizeMapper;

    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;
    @Inject
    private CustomTRAOrderMapper traOrderMapper;
    @Inject
    private CustomTRACampaignMapper customTRACampaignMapper;

    public CRMAccount createCrmAcountEntity(CrmAccountPT crmAccountPT,CORNetwork corNetwork) {
        CRMAccount crmAccount = new CRMAccount();
        crmAccount.setId(crmAccountPT.getId());
        return crmAccount
            .paymentDelay(Long.valueOf(crmAccountPT.getPaymentDelay()))
            .idNumber1(crmAccountPT.getIdNumber1())
            .idNumber2(crmAccountPT.getIdNumber2())
            .name(crmAccountPT.getName())
            .getShortName(crmAccountPT.getShortName())
            .vatNumber(crmAccountPT.getVatNumber())
            .campaigns(new HashSet<>())
            .orders(crmAccountPT.getOrders().stream().map(traOrderPT -> traOrderMapper.trasnformDTOtoEntity(traOrderPT)).collect(toSet()))
            .tasks(crmAccountPT.getTasks().stream().map(crmTaskPT -> customCRMTaskMapper.createTaskEntity(crmTaskPT)).collect(toSet()));
    }

    public CRMAccount createCrmAcountEntity(TraCustomerPT traCustomerPT, CORNetwork network) {
        CRMAccount crmAccount = new CRMAccount();
        crmAccount.setId(traCustomerPT.getId());
        return crmAccount
            .paymentDelay(Long.valueOf(traCustomerPT.getPaymentDelay()))
            .idNumber1(traCustomerPT.getIdNumber1())
            .idNumber2(traCustomerPT.getIdNumber2())
            .name(traCustomerPT.getName())
            .getShortName(traCustomerPT.getShortName())
            .vatNumber(traCustomerPT.getVatNumber())
            .network(network);
    }

    public TraCustomerPT createCustomerTrafficDTO(CRMAccount traCustomerPT) {
        TraCustomerPT crmAccount = new TraCustomerPT();
        crmAccount.setId(traCustomerPT.getId());
        return crmAccount.
            id(traCustomerPT.getId())
            .name(traCustomerPT.getName())
            .idNumber1(traCustomerPT.getIdNumber1())
            .idNumber2(traCustomerPT.getIdNumber2())
            .shortName(traCustomerPT.getGetShortName())
            .vatNumber(traCustomerPT.getVatNumber())
            .paymentDelay(Math.toIntExact(traCustomerPT.getPaymentDelay()))
            .industry(industryMapper.tRAIndustryToTRAIndustryDTO(traCustomerPT.getIndustry()))
            .account(traCustomerPT.getKeeper())
            .size(customCORSizeMapper.cORSizeToCORSizeDTO(traCustomerPT.getSize()))
            .range(customCORRangeMapper.cORRangeToCORRangeDTO(traCustomerPT.getRange()))
            .adress(corAddressMapper.cORAddressToCORAddressDTO(traCustomerPT.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(traCustomerPT.getPerson()))
            .area(customCORAreaMapper.cORAreaToCORAreaDTO(traCustomerPT.getArea()));
    }

    public CrmAccountPT buildContactDTOFromEntities(CRMAccount crmAccount) {
        return new CrmAccountPT()
            .id(crmAccount.getId())
            .name(crmAccount.getName())
            .idNumber1(crmAccount.getIdNumber1())
            .idNumber2(crmAccount.getIdNumber2())
            .shortName(crmAccount.getGetShortName())
            .vatNumber(crmAccount.getVatNumber())
            .paymentDelay(Math.toIntExact(crmAccount.getPaymentDelay()))
            .industry(industryMapper.tRAIndustryToTRAIndustryDTO(crmAccount.getIndustry()))
            .account(crmAccount.getKeeper())
            .size(customCORSizeMapper.cORSizeToCORSizeDTO(crmAccount.getSize()))
            .range(customCORRangeMapper.cORRangeToCORRangeDTO(crmAccount.getRange()))
            .adress(corAddressMapper.cORAddressToCORAddressDTO(crmAccount.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(crmAccount.getPerson()))
            .area(customCORAreaMapper.cORAreaToCORAreaDTO(crmAccount.getArea()))
            .tasks(crmAccount.getTasks());

    }


}
