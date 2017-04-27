package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreAddressPT;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.service.dto.thin.TraCustomerThinPT;
import io.protone.custom.service.dto.thin.TraInvoiceCustomerThinPT;
import io.protone.domain.*;
import io.protone.service.dto.TraInvoiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = CustomTraOrderMapper.class)
public interface CustomTraInvoiceMapper {


    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "status", target = "statusId")
    @Mapping(source = "orders", target = "orders")
    TraInvoicePT DB2DTO(TraInvoice traInvoice);

    List<TraInvoicePT> DBs2DTOs(List<TraInvoice> traInvoices);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "orders", target = "orders")
    TraInvoice DTO2DB(TraInvoicePT traInvoiceDTO);

    List<TraInvoice> DTOs2DBs(List<TraInvoicePT> traInvoiceDTOs);

    CrmAccount crmAccountFromTraInvoiceCustomerThinPT(TraInvoiceCustomerThinPT coreUserThinPT);

    TraInvoiceCustomerThinPT traInvoiceCustomerThinPTFromCrmAccount(CrmAccount coreUserThinPT);

    CorAddress corAddressFromCoreAddressPT(CoreAddressPT coreUserThinPT);

    CoreAddressPT coreAddressPTFromCorAddress(CorAddress coreUserThinPT);


}
