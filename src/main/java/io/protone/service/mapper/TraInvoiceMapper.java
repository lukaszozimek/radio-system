package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TraInvoiceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TraInvoice and its DTO TraInvoiceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraInvoiceMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "status.id", target = "statusId")
    TraInvoiceDTO traInvoiceToTraInvoiceDTO(TraInvoice traInvoice);

    List<TraInvoiceDTO> traInvoicesToTraInvoiceDTOs(List<TraInvoice> traInvoices);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "statusId", target = "status")
    @Mapping(target = "orders", ignore = true)
    TraInvoice traInvoiceDTOToTraInvoice(TraInvoiceDTO traInvoiceDTO);

    List<TraInvoice> traInvoiceDTOsToTraInvoices(List<TraInvoiceDTO> traInvoiceDTOs);

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

    default TraInvoiceStatus traInvoiceStatusFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraInvoiceStatus traInvoiceStatus = new TraInvoiceStatus();
        traInvoiceStatus.setId(id);
        return traInvoiceStatus;
    }
}
