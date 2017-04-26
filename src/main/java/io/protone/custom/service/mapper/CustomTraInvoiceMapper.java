package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.CorDictionary;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraInvoice;
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
@Mapper(componentModel = "spring", uses = {})
public interface CustomTraInvoiceMapper {


    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "orders", target = "orders")
    TraInvoicePT traInvoiceToTraInvoiceDTO(TraInvoice traInvoice);

    List<TraInvoicePT> traInvoicesToTraInvoiceDTOs(List<TraInvoice> traInvoices);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "orders", target = "orders")
    TraInvoice traInvoiceDTOToTraInvoice(TraInvoicePT traInvoiceDTO);

    List<TraInvoice> traInvoiceDTOsToTraInvoices(List<TraInvoicePT> traInvoiceDTOs);

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

    default CorDictionary corDictionaryFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorDictionary corDictionary = new CorDictionary();
        corDictionary.setId(id);
        return corDictionary;
    }


}
