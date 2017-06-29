package io.protone.traffic.mapper;

import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorAddressMapper;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.traffic.api.dto.TraInvoiceDTO;
import io.protone.traffic.domain.TraInvoice;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {TraOrderMapper.class, CorDictionaryMapper.class, CorAddressMapper.class})
public interface TraInvoiceMapper {


    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "status", target = "statusId")
    @Mapping(source = "orders", target = "orders")
    TraInvoiceDTO DB2DTO(TraInvoice traInvoice);

    List<TraInvoiceDTO> DBs2DTOs(List<TraInvoice> traInvoices);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "orders", target = "orders")
    TraInvoice DTO2DB(TraInvoiceDTO traInvoiceDTO, @Context CorNetwork corNetwork);

    default List<TraInvoice> DTOs2DBs(List<TraInvoiceDTO> traInvoiceDTOs, CorNetwork networkId) {
        List<TraInvoice> traOrders = new ArrayList<>();
        if (traInvoiceDTOs.isEmpty() || traInvoiceDTOs == null) {
            return null;
        }
        for (TraInvoiceDTO dto : traInvoiceDTOs) {
            traOrders.add(DTO2DB(dto, networkId));
        }
        return traOrders;
    }

    @AfterMapping
    default void traInvoicePTToTraInvoiceAfterMapping(TraInvoiceDTO dto, @MappingTarget TraInvoice entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
