package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchAttachmentDTO;
import io.protone.scheduler.api.dto.SchQueueParamsDTO;
import io.protone.scheduler.domain.enumeration.ObjectTypeEnum;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 @param <D> - DTO type parameter.
 @param <E> - Entity type parameter.
 */

public interface SchEntityMapper<D, E> {

    public E toEntity(D dto);

    public D toDto(E entity);

    public List <E> toEntity(List<D> dtoList);

    public List <D> toDto(List<E> entityList);

    default SchQueueParamsDTO.ObjectTypeEnum mapFadeTypeEnum(ObjectTypeEnum value) {

        if (value.compareTo(ObjectTypeEnum.OT_BLOCK) == 0)
            return SchQueueParamsDTO.ObjectTypeEnum.BLOCK;
        else if (value.compareTo(ObjectTypeEnum.OT_CLOCK) == 0)
            return SchQueueParamsDTO.ObjectTypeEnum.CLOCK;
        else if (value.compareTo(ObjectTypeEnum.OT_EMISSION) == 0)
            return SchQueueParamsDTO.ObjectTypeEnum.EMISSION;
        else if (value.compareTo(ObjectTypeEnum.OT_EVENT) == 0)
            return SchQueueParamsDTO.ObjectTypeEnum.EMISSION;
        else
            return SchQueueParamsDTO.ObjectTypeEnum.UNKNOWN;
    }

    default ObjectTypeEnum mapFadeTypeEnum(SchQueueParamsDTO.ObjectTypeEnum value) {
        if (value.compareTo(SchQueueParamsDTO.ObjectTypeEnum.BLOCK) == 0)
            return ObjectTypeEnum.OT_BLOCK;
        else if (value.compareTo(SchQueueParamsDTO.ObjectTypeEnum.CLOCK) == 0)
            return ObjectTypeEnum.OT_CLOCK;
        else if (value.compareTo(SchQueueParamsDTO.ObjectTypeEnum.EMISSION) == 0)
            return ObjectTypeEnum.OT_EMISSION;
        else if (value.compareTo(SchQueueParamsDTO.ObjectTypeEnum.EVENT) == 0)
            return ObjectTypeEnum.OT_EVENT;
        else
            return ObjectTypeEnum.OT_UNKNOWN;

    }

}
