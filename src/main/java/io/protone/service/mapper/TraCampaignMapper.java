package io.protone.service.mapper;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    TraCampaign DTO2DB(TraCampaignPT traCampaignDTO);

    List<TraCampaign> DTOs2DBs(List<TraCampaignPT> traCampaignDTOs);


}
