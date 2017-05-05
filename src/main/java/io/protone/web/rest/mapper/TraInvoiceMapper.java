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

    default LibMarkerDTO.MarkerTypeEnum mapLibMarkerPT_MarkerTypeEnum(LibMarkerTypeEnum value) {

        if (value.compareTo(LibMarkerTypeEnum.MT_BASIC) == 0) {
            return LibMarkerDTO.MarkerTypeEnum.BASIC;
        } else if (value.compareTo(LibMarkerTypeEnum.MT_CUSTOM) == 0) {
            return LibMarkerDTO.MarkerTypeEnum.CUSTOM;
        } else if (value.compareTo(LibMarkerTypeEnum.MT_FADE) == 0) {
            return LibMarkerDTO.MarkerTypeEnum.FADE;
        } else if (value.compareTo(LibMarkerTypeEnum.MT_HOOK) == 0) {
            return LibMarkerDTO.MarkerTypeEnum.HOOK;
        } else if (value.compareTo(LibMarkerTypeEnum.MT_INTRO) == 0) {
            return LibMarkerDTO.MarkerTypeEnum.INTRO;
        } else if (value.compareTo(LibMarkerTypeEnum.MT_LOOP) == 0) {
            return LibMarkerDTO.MarkerTypeEnum.LOOP;
        } else {
            return LibMarkerDTO.MarkerTypeEnum.CUSTOM;
        }
    }

    default LibMarkerTypeEnum mapLibMarkerTypeEnum(LibMarkerDTO.MarkerTypeEnum value) {

        if (value.compareTo(LibMarkerDTO.MarkerTypeEnum.BASIC) == 0) {
            return LibMarkerTypeEnum.MT_BASIC;
        } else if (value.compareTo(LibMarkerDTO.MarkerTypeEnum.CUSTOM) == 0) {
            return LibMarkerTypeEnum.MT_CUSTOM;
        } else if (value.compareTo(LibMarkerDTO.MarkerTypeEnum.FADE) == 0) {
            return LibMarkerTypeEnum.MT_FADE;
        } else if (value.compareTo(LibMarkerDTO.MarkerTypeEnum.HOOK) == 0) {
            return LibMarkerTypeEnum.MT_HOOK;
        } else if (value.compareTo(LibMarkerDTO.MarkerTypeEnum.INTRO) == 0) {
            return LibMarkerTypeEnum.MT_INTRO;
        } else if (value.compareTo(LibMarkerDTO.MarkerTypeEnum.LOOP) == 0) {
            return LibMarkerTypeEnum.MT_LOOP;
        } else {
            return LibMarkerTypeEnum.MT_CUSTOM;
        }
    }
}
