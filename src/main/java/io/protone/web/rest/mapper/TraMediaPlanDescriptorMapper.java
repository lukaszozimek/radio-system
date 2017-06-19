package io.protone.web.rest.mapper;

import io.protone.service.traffic.mediaplan.descriptor.TraMediaPlanDescriptor;
import io.protone.web.rest.dto.traffic.TraMediaPlanDescriptorDTO;
import org.mapstruct.Mapper;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
@Mapper(componentModel = "spring", uses = {TraOrderMapper.class})
public interface TraMediaPlanDescriptorMapper {

    TraMediaPlanDescriptor DTO2DB(TraMediaPlanDescriptorDTO traEmissionDTO);

}
