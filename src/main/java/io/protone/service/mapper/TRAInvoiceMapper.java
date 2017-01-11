package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TRAInvoiceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TRAInvoice and its DTO TRAInvoiceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TRAInvoiceMapper {

    @Mapping(source = "order.id", target = "orderId")
    TRAInvoiceDTO tRAInvoiceToTRAInvoiceDTO(TRAInvoice tRAInvoice);

    List<TRAInvoiceDTO> tRAInvoicesToTRAInvoiceDTOs(List<TRAInvoice> tRAInvoices);

    @Mapping(source = "orderId", target = "order")
    TRAInvoice tRAInvoiceDTOToTRAInvoice(TRAInvoiceDTO tRAInvoiceDTO);

    List<TRAInvoice> tRAInvoiceDTOsToTRAInvoices(List<TRAInvoiceDTO> tRAInvoiceDTOs);

    default TRAOrder tRAOrderFromId(Long id) {
        if (id == null) {
            return null;
        }
        TRAOrder tRAOrder = new TRAOrder();
        tRAOrder.setId(id);
        return tRAOrder;
    }
}
