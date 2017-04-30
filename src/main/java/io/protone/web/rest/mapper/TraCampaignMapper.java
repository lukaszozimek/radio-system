package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.LibMarkerPT;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.dto.TraOrderPT;
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
    TraCampaignPT DB2DTO(TraCampaign traCampaign);

    List<TraCampaignPT> DBs2DTOs(List<TraCampaign> traCampaigns);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "orders", target = "orders")
    TraCampaign DTO2DB(TraCampaignPT traCampaignDTO, @Context CorNetwork corNetwork);

    default List<TraCampaign> DTOs2DBs(List<TraCampaignPT> traCampaignPT, CorNetwork networkId) {
        List<TraCampaign> traCampaigns = new ArrayList<>();
        if (traCampaignPT.isEmpty() || traCampaignPT == null) {
            return null;
        }
        for (TraCampaignPT dto : traCampaignPT) {
            traCampaigns.add(DTO2DB(dto, networkId));
        }
        return traCampaigns;
    }

    @AfterMapping
    default void traCampaignPTToTraCampaignAfterMapping(TraOrderPT dto, @MappingTarget TraOrder entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

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
