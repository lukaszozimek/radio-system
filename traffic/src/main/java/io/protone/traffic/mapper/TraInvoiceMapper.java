package io.protone.traffic.mapper;

import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorAddressMapper;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.traffic.api.dto.TraInvoiceDTO;
import io.protone.traffic.api.dto.thin.TraInvoiceThinDTO;
import io.protone.traffic.domain.TraInvoice;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {TraOrderMapper.class, CorDictionaryMapper.class, CorAddressMapper.class, TraCompanyMapper.class})
public interface TraInvoiceMapper {


    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "status", target = "statusId")
    @Mapping(source = "orders", target = "orders")
    @Mapping(source = "company", target = "traCompany")
    TraInvoiceDTO DB2DTO(TraInvoice traInvoice);

    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "status", target = "statusId")
    TraInvoiceThinDTO DB2ThinDTO(TraInvoice traInvoice);

    List<TraInvoiceThinDTO> DBs2ThinDTOs(List<TraInvoice> traInvoices);

    List<TraInvoiceDTO> DBs2DTOs(List<TraInvoice> traInvoices);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "orders", target = "orders")
    @Mapping(source = "traCompany", target = "company")
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
