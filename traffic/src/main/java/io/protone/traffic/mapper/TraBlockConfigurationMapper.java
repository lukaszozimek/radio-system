package io.protone.traffic.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaItem;
import io.protone.traffic.api.dto.TraBlockConfigurationDTO;
import io.protone.traffic.domain.TraBlockConfiguration;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Mapper(componentModel = "spring", uses = {TraPriceMapper.class})
public interface TraBlockConfigurationMapper {

    @Mapping(source = "day", target = "day")
    @Mapping(source = "blockStartSound.id", target = "startBlockSoundId")
    @Mapping(source = "blockEndSound.id", target = "stopBlockSoundId")
    @Mapping(source = "price", target = "price")
    TraBlockConfigurationDTO DB2DTO(TraBlockConfiguration traBlockConfiguration);

    List<TraBlockConfigurationDTO> DBs2DTOs(List<TraBlockConfiguration> traBlockConfigurations);

    @Mapping(source = "day", target = "day")
    @Mapping(source = "startBlockSoundId", target = "blockStartSound.id")
    @Mapping(source = "stopBlockSoundId", target = "blockEndSound.id")
    @Mapping(source = "price", target = "price")
    TraBlockConfiguration DTO2DB(TraBlockConfigurationDTO traBlockConfigurationDTO, @Context CorChannel corChannel);

    default List<TraBlockConfiguration> DTOs2DBs(List<TraBlockConfigurationDTO> traBlockConfigurationDTOS, @Context CorChannel corChannel) {
        List<TraBlockConfiguration> traBlockConfigurations = new ArrayList<>();
        if (traBlockConfigurationDTOS.isEmpty() || traBlockConfigurationDTOS == null) {
            return null;
        }
        for (TraBlockConfigurationDTO dto : traBlockConfigurationDTOS) {
            traBlockConfigurations.add(DTO2DB(dto, corChannel));
        }
        return traBlockConfigurations;
    }

    default Long traBlockConfigurationDTOStartBlockSoundIdFromTraBlockConfiguration(TraBlockConfiguration traBlockConfiguration) {
        if (traBlockConfiguration == null || traBlockConfiguration.getBlockStartSound() == null) {
            return null;
        }
        return traBlockConfiguration.getBlockStartSound().getId();
    }

    default Long traBlockConfigurationDTOStopBlockSoundIdFromTraBlockConfiguration(TraBlockConfiguration traBlockConfiguration) {
        if (traBlockConfiguration == null || traBlockConfiguration.getBlockEndSound() == null) {
            return null;
        }
        return traBlockConfiguration.getBlockEndSound().getId();
    }


    @AfterMapping
    default void traBlockConfigurationDTOToTraBlockConfigurationAfterMapping(TraBlockConfigurationDTO dto, @MappingTarget TraBlockConfiguration entity, @Context CorChannel corChannel) {
        //TODO:Mapstruct doesn't map concret fieldName it couse TransientFiled Exception in hibernate
        if (dto.getStopBlockSoundId() != null) {
            LibMediaItem libMediaItem = new LibMediaItem();
            libMediaItem.setId(dto.getStopBlockSoundId());
            entity.setBlockEndSound(libMediaItem);
        } else {
            entity.setBlockEndSound(null);
        }
        if (dto.getStartBlockSoundId() != null) {
            LibMediaItem libMediaItem = new LibMediaItem();
            libMediaItem.setId(dto.getStartBlockSoundId());
            entity.setBlockStartSound(libMediaItem);
        } else {

            entity.setBlockStartSound(null);
        }

        entity.setChannel(corChannel);
    }
}
