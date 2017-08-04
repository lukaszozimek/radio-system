package io.protone.scheduler.to.delete.factory;

import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.api.dto.SchClockThinDTO;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchEmission;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


public class SchClockFactory {

    private static final PodamFactory factory = new PodamFactoryImpl();

    public static SchClock produceEntity() {

        // Fill entity
        SchClock clock = factory.manufacturePojo(SchClock.class);

        clock.addBlock(sampleBlock()); //Block 1 @ clock
        clock.addBlock(sampleBlock()); //Block 2 @ clock
        clock.addBlock(sampleBlock()); //Block 3 @ clock

        clock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 1 @ clock
        clock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 2 @ clock
        clock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 3 @ clock

        return clock;
    }

    private static SchBlock sampleBlock() {

        SchBlock sampleBlock = factory.manufacturePojo(SchBlock.class);

        sampleBlock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 1 @ sampleBlock
        sampleBlock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 2 @ sampleBlock
        sampleBlock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 3 @ sampleBlock

        return sampleBlock;
    }

    public static SchClockDTO produceDTO() {
        // Fill DTO
        SchClockDTO clockDTO = factory.manufacturePojo(SchClockDTO.class);

        clockDTO.addBlocksItem(sampleBlockDTO()); //Block 1 @ clock
        clockDTO.addBlocksItem(sampleBlockDTO()); //Block 2 @ clock
        clockDTO.addBlocksItem(sampleBlockDTO()); //Block 3 @ clock

        clockDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 1 @ clock
        clockDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 2 @ clock
        clockDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 3 @ clock

        return clockDTO;
    }

    private static SchBlockDTO sampleBlockDTO() {

        SchBlockDTO sampleBlock = factory.manufacturePojo(SchBlockDTO.class);

        sampleBlock.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 1 @ sampleBlock
        sampleBlock.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 2 @ sampleBlock
        sampleBlock.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 3 @ sampleBlock

        return sampleBlock;
    }

    public static SchClockThinDTO produceThinDTO() {
        // Fill DTO
        SchClockThinDTO clockDTO = factory.manufacturePojo(SchClockThinDTO.class);
        return clockDTO;
    }

}