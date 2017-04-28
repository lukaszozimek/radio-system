package io.protone.service.mapper;

import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.TraInvoice;
import io.protone.service.mapper.CorAddressMapper;
import io.protone.service.mapper.CorDictionaryMapper;
import io.protone.service.mapper.TraOrderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {TraOrderMapper.class, CorDictionaryMapper.class, CorAddressMapper.class})
public interface TraInvoiceMapper {


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

}
