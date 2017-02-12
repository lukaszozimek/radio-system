package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TraInvoiceStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TraInvoiceStatus and its DTO TraInvoiceStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraInvoiceStatusMapper {

    TraInvoiceStatusDTO traInvoiceStatusToTraInvoiceStatusDTO(TraInvoiceStatus traInvoiceStatus);

    List<TraInvoiceStatusDTO> traInvoiceStatusesToTraInvoiceStatusDTOs(List<TraInvoiceStatus> traInvoiceStatuses);

    TraInvoiceStatus traInvoiceStatusDTOToTraInvoiceStatus(TraInvoiceStatusDTO traInvoiceStatusDTO);

    List<TraInvoiceStatus> traInvoiceStatusDTOsToTraInvoiceStatuses(List<TraInvoiceStatusDTO> traInvoiceStatusDTOs);
}
