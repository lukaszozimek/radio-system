package io.protone.application.web.rest.mapper.factory;

import io.protone.application.ProtoneApp;
import io.protone.scheduler.api.dto.*;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchGrid;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class SchGridFactory {

    private static final PodamFactory factory = new PodamFactoryImpl();

    public static SchGrid produceEntity() {

        // Fill entity
        SchGrid grid = factory.manufacturePojo(SchGrid.class);

        grid.addClock(SchClockFactory.produceEntity()); //Clock 1 @ grid
        grid.addClock(SchClockFactory.produceEntity()); //Clock 2 @ grid
        grid.addClock(SchClockFactory.produceEntity()); //Clock 3 @ grid

        return grid;
    }

    public static SchGridDTO produceDTO() {
        // Fill DTO
        SchGridDTO gridDTO = factory.manufacturePojo(SchGridDTO.class);

        gridDTO.addClock(SchClockFactory.produceThinDTO()); //Clock 1 @ grid
        gridDTO.addClock(SchClockFactory.produceThinDTO()); //Clock 1 @ grid
        gridDTO.addClock(SchClockFactory.produceThinDTO()); //Clock 1 @ grid

        return gridDTO;
    }

}