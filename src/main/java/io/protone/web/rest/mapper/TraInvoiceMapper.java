package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.LibMarkerPT;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.TraInvoice;
import io.protone.domain.enumeration.LibMarkerTypeEnum;
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
    default LibMarkerPT.MarkerTypeEnum mapLibMarkerPT_MarkerTypeEnum(LibMarkerTypeEnum value) {

        if (value.compareTo(LibMarkerTypeEnum.MT_BASIC) == 0) {
            return LibMarkerPT.MarkerTypeEnum.BASIC;
        } else if (value.compareTo(LibMarkerTypeEnum.MT_CUSTOM) == 0) {
            return LibMarkerPT.MarkerTypeEnum.CUSTOM;
        } else if (value.compareTo(LibMarkerTypeEnum.MT_FADE) == 0) {
            return LibMarkerPT.MarkerTypeEnum.FADE;
        } else if (value.compareTo(LibMarkerTypeEnum.MT_HOOK) == 0) {
            return LibMarkerPT.MarkerTypeEnum.HOOK;
        } else if (value.compareTo(LibMarkerTypeEnum.MT_INTRO) == 0) {
            return LibMarkerPT.MarkerTypeEnum.INTRO;
        } else if (value.compareTo(LibMarkerTypeEnum.MT_LOOP) == 0) {
            return LibMarkerPT.MarkerTypeEnum.LOOP;
        } else {
            return LibMarkerPT.MarkerTypeEnum.CUSTOM;
        }
    }

    default LibMarkerTypeEnum mapLibMarkerTypeEnum(LibMarkerPT.MarkerTypeEnum value) {

        if (value.compareTo(LibMarkerPT.MarkerTypeEnum.BASIC) == 0) {
            return LibMarkerTypeEnum.MT_BASIC;
        } else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.CUSTOM) == 0) {
            return LibMarkerTypeEnum.MT_CUSTOM;
        } else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.FADE) == 0) {
            return LibMarkerTypeEnum.MT_FADE;
        } else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.HOOK) == 0) {
            return LibMarkerTypeEnum.MT_HOOK;
        } else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.INTRO) == 0) {
            return LibMarkerTypeEnum.MT_INTRO;
        } else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.LOOP) == 0) {
            return LibMarkerTypeEnum.MT_LOOP;
        } else {
            return LibMarkerTypeEnum.MT_CUSTOM;
        }
    }
}
