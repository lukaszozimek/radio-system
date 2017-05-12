package io.protone.web.rest.mapper;

import io.protone.web.rest.dto.library.LibMarkerDTO;
import io.protone.web.rest.dto.traffic.TraCampaignDTO;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibMarkerTypeEnum;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {TraOrderMapper.class, CorDictionaryMapper.class})
public interface TraCampaignMapper {

    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "orders", target = "orders")
    TraCampaignDTO DB2DTO(TraCampaign traCampaign);

    List<TraCampaignDTO> DBs2DTOs(List<TraCampaign> traCampaigns);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "orders", target = "orders")
    TraCampaign DTO2DB(TraCampaignDTO traCampaignDTO, @Context CorNetwork corNetwork);

    default List<TraCampaign> DTOs2DBs(List<TraCampaignDTO> traCampaignDTO, CorNetwork networkId) {
        List<TraCampaign> traCampaigns = new ArrayList<>();
        if (traCampaignDTO.isEmpty() || traCampaignDTO == null) {
            return null;
        }
        for (TraCampaignDTO dto : traCampaignDTO) {
            traCampaigns.add(DTO2DB(dto, networkId));
        }
        return traCampaigns;
    }

    @AfterMapping
    default void traCampaignPTToTraCampaignAfterMapping(TraCampaignDTO dto, @MappingTarget TraCampaign entity, @Context CorNetwork corNetwork) {
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
