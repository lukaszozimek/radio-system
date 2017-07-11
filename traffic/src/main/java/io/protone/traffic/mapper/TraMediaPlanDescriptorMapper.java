package io.protone.traffic.mapper;

import io.protone.traffic.api.dto.TraMediaPlanDescriptorDTO;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
import org.mapstruct.Mapper;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
@Mapper(componentModel = "spring", uses = {TraOrderMapper.class})
public interface TraMediaPlanDescriptorMapper {

    TraMediaPlanDescriptor DTO2DB(TraMediaPlanDescriptorDTO traEmissionDTO);

}
