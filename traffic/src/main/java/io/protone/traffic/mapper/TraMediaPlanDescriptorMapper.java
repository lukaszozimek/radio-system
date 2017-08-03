package io.protone.traffic.mapper;

import io.protone.library.mapper.LibItemMapper;
import io.protone.traffic.api.dto.TraMediaPlanDescriptorDTO;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
@Mapper(componentModel = "spring", uses = {TraOrderMapper.class, TraMediaPlanTemplateMapper.class, LibItemMapper.class})
public interface TraMediaPlanDescriptorMapper {

    @Mapping(target = "traMediaPlanTemplate", source = "traMediaPlanTemplateDTO")
    @Mapping(target = "libMediaItem", source = "libMediaItemThinDTO")
    TraMediaPlanDescriptor DTO2DB(TraMediaPlanDescriptorDTO traEmissionDTO);

}
