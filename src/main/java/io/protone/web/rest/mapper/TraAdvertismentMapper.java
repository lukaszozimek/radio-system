package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.web.rest.dto.thin.TraAdvertisementThinDTO;
import io.protone.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CorDictionaryMapper.class, LibItemMapper.class})
public interface TraAdvertismentMapper {
    @Mapping(source = "mediaItem", target = "mediaItemId")
    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "type", target = "typeId")
    TraAdvertisementPT DB2DTO(TraAdvertisement traAdvertisement);

    List<TraAdvertisementPT> DBs2DTOs(List<TraAdvertisement> traAdvertisements);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "industryId", target = "industry")
    @Mapping(source = "typeId", target = "type")
    TraAdvertisement DTO2DB(TraAdvertisementPT traAdvertisementDTO);

    List<TraAdvertisement> DTOs2DBs(List<TraAdvertisementPT> traAdvertisementDTOs);

    TraAdvertisement traAdvertisementFromTraAdvertisementThinPT(TraAdvertisementThinDTO coreUserThinPT);

    TraAdvertisementThinDTO traAdvertisementThinPTFromTraAdvertisement(TraAdvertisement coreUserThinPT);



}
