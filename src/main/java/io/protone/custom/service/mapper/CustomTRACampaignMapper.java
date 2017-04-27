package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.domain.*;
import io.protone.service.dto.TraCampaignDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = CustomTraOrderMapper.class)
public interface CustomTRACampaignMapper {

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
