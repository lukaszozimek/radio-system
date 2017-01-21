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

    TRAInvoiceDTO tRAInvoiceToTRAInvoiceDTO(TRAInvoice tRAInvoice);

    List<TRAInvoiceDTO> tRAInvoicesToTRAInvoiceDTOs(List<TRAInvoice> tRAInvoices);

    TRAInvoice tRAInvoiceDTOToTRAInvoice(TRAInvoiceDTO tRAInvoiceDTO);

    List<TRAInvoice> tRAInvoiceDTOsToTRAInvoices(List<TRAInvoiceDTO> tRAInvoiceDTOs);


}
