package io.protone.scheduler.to.delete.factory;

import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.domain.SchGrid;
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