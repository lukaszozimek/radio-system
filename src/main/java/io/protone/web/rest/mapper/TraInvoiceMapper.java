package io.protone.web.rest.mapper;

import io.protone.web.rest.dto.library.LibMarkerDTO;
import io.protone.web.rest.dto.traffic.TraInvoiceDTO;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraInvoice;
import io.protone.domain.enumeration.LibMarkerTypeEnum;
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
