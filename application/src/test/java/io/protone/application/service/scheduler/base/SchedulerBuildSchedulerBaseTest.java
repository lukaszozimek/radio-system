package io.protone.application.service.scheduler.base;

import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.scheduler.domain.SchClockConfiguration;
import io.protone.scheduler.domain.SchEmissionConfiguration;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.domain.SchGridClockConfiguration;
import io.protone.scheduler.repository.SchClockConfigurationRepository;
import io.protone.scheduler.repository.SchEmissionConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lukaszozimek on 14/08/2017.
 */
public class SchedulerBuildSchedulerBaseTest extends SchedulerBaseTest {
    protected Map<DayOfWeek, CorDayOfWeekEnum> corDayOfWeekEnumMap = new HashMap<>();

    @Autowired
    private SchEmissionConfigurationRepository schEmissionConfigurationRepository;

    @Autowired
    private SchClockConfigurationRepository schClockConfigurationRepository;

    public void setUp() throws Exception {
        super.setUp();

        corDayOfWeekEnumMap = new HashMap<>();
        corDayOfWeekEnumMap.put(DayOfWeek.MONDAY, CorDayOfWeekEnum.DW_MONDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.TUESDAY, CorDayOfWeekEnum.DW_TUESDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.WEDNESDAY, CorDayOfWeekEnum.DW_WEDNESDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.THURSDAY, CorDayOfWeekEnum.DW_THURSDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.FRIDAY, CorDayOfWeekEnum.DW_FRIDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.SATURDAY, CorDayOfWeekEnum.DW_SATURDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.SUNDAY, CorDayOfWeekEnum.DW_SUNDAY);
    }

    protected SchGrid buildGridForDay(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.channel(corChannel);
        schGrid.network(corNetwork);
        return schGrid;
    }

    protected SchGrid buildGridForDayWitClock(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.addClock(new SchGridClockConfiguration().sequence(1L).schClockConfiguration(buildClockConfiguration()));
        schGrid.channel(corChannel);
        schGrid.network(corNetwork);
        return schGrid;
    }

    protected SchGrid buildGridForDayWitClockWithNestedEvents(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.addClock(new SchGridClockConfiguration().sequence(1L).schClockConfiguration(buildClockWithNestedEventsConfiguration()));
        schGrid.channel(corChannel);
        schGrid.network(corNetwork);
        return schGrid;
    }


    protected SchGrid buildGridForDayWitClockMusicAndImportEventsAndEmissionsConfiguration(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.addClock(new SchGridClockConfiguration().sequence(1L).schClockConfiguration(buildClockWithNestedMusicAndImportEventsAndEmissionsConfiguration()));
        schGrid.channel(corChannel);
        schGrid.network(corNetwork);
        return schGrid;
    }

    protected SchClockConfiguration buildClockConfiguration() {
        SchClockConfiguration schClockConfiguration = new SchClockConfiguration();
        schClockConfiguration.setSequence(1L);
        schClockConfiguration.length(3600000L);
        schClockConfiguration.channel(corChannel);
        schClockConfiguration.network(corNetwork);
        schClockConfiguration = schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
        schClockConfiguration.emissions(buildQuatreEmissionConfiguration(schClockConfiguration, 4,1));
        schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
        return schClockConfiguration;
    }

    protected SchClockConfiguration buildClockWithNestedEventsConfiguration() {
        SchClockConfiguration schClockConfiguration = new SchClockConfiguration();
        schClockConfiguration.length(3600000L);
        schClockConfiguration.setSequence(1L);
        schClockConfiguration.channel(corChannel);
        schClockConfiguration.network(corNetwork);
        schClockConfiguration = schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
        schClockConfiguration.emissions(buildQuatreEmissionConfiguration(schClockConfiguration, 2, 4));
        schClockConfiguration.events(buildNestedSetEventsWithLenght30minoutes(schClockConfiguration));
        return schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
    }


    protected SchClockConfiguration buildClockWithNestedMusicAndImportEventsAndEmissionsConfiguration() {
        SchClockConfiguration schClockConfiguration = new SchClockConfiguration();
        schClockConfiguration.setSequence(1L);
        schClockConfiguration.length(7200000L);
        schClockConfiguration.channel(corChannel);
        schClockConfiguration.network(corNetwork);
        schClockConfiguration = schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
        schClockConfiguration.emissions(buildQuatreEmissionConfiguration(schClockConfiguration, 2,4));
        schClockConfiguration.events(buildNestedSetEventsWithLenght30minoutesWithMusicImport(schClockConfiguration));
        schClockConfiguration = schClockConfigurationRepository.saveAndFlush(schClockConfiguration);

        return schClockConfiguration;
    }


    private Set<SchEmissionConfiguration> buildQuatreEmissionConfiguration(SchClockConfiguration schClockConfiguration, int quaterNumber, long startSequence) {
        Set<SchEmissionConfiguration> emissionConfigurations = new HashSet<>();
        for (int i = 0; i < quaterNumber; i++) {
            SchEmissionConfiguration schEmissionConfiguration = new SchEmissionConfiguration();
            schEmissionConfiguration.length(900000L);
            schEmissionConfiguration.mediaItem(libMediaItemList.get(0));
            schEmissionConfiguration.seq(startSequence + i);
            schEmissionConfiguration.setClock(schClockConfiguration);
            schEmissionConfiguration.channel(corChannel);
            schEmissionConfiguration.network(corNetwork);
            emissionConfigurations.add(schEmissionConfigurationRepository.saveAndFlush(schEmissionConfiguration));
        }
        return emissionConfigurations;
    }


}
