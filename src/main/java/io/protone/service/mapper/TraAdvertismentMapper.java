package io.protone.service.mapper;

import io.protone.custom.service.TraCustomerService;
import io.protone.custom.service.dto.CorDictionaryPT;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.LibMediaItemPT;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.dto.thin.LibMediaItemThinPt;
import io.protone.custom.service.dto.thin.TraAdvertisementThinPT;
import io.protone.custom.service.dto.thin.TraCustomerThinPT;
import io.protone.domain.*;
import io.protone.service.dto.TraAdvertisementDTO;
import io.protone.service.mapper.CorDictionaryMapper;
import io.protone.service.mapper.LibItemMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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

    TraAdvertisement traAdvertisementFromTraAdvertisementThinPT(TraAdvertisementThinPT coreUserThinPT);

    TraAdvertisementThinPT traAdvertisementThinPTFromTraAdvertisement(TraAdvertisement coreUserThinPT);



}
