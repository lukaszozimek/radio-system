package io.protone.scheduler.service.schedule.build;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.*;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import io.protone.scheduler.service.*;
import io.protone.scheduler.service.schedule.factory.SchClockBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;


@Service
public class SchScheduleBuilderService {
    private final Logger log = LoggerFactory.getLogger(SchScheduleBuilderService.class);
    @Inject
    private SchGridService schGridService;

    @Inject
    private SchClockService schClockService;
    @Inject
    private SchLogService schLogService;

    @Inject
    private LibFileItemService libFileItemService;

    @Inject
    private SchPlaylistService schPlaylistService;

    @Inject
    private SchParseLogService schParseLogService;


    @Inject
    private SchClockBuilder schClockBuilder;
    private Map<DayOfWeek, CorDayOfWeekEnum> corDayOfWeekEnumMap;

    @PostConstruct
    public void initialize() {
        corDayOfWeekEnumMap = new HashMap<>();
        corDayOfWeekEnumMap.put(DayOfWeek.MONDAY, CorDayOfWeekEnum.DW_MONDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.TUESDAY, CorDayOfWeekEnum.DW_TUESDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.WEDNESDAY, CorDayOfWeekEnum.DW_WEDNESDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.THURSDAY, CorDayOfWeekEnum.DW_THURSDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.FRIDAY, CorDayOfWeekEnum.DW_FRIDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.SATURDAY, CorDayOfWeekEnum.DW_SATURDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.SUNDAY, CorDayOfWeekEnum.DW_SUNDAY);
    }

    public SchSchedule buildScheduleForDate(LocalDate localDate, String gridShortName, String networkShortcut, String channelShortcut) throws Exception {
        SchGrid schGrid = this.schGridService.findSchGridForNetworkAndChannelAndShortName(networkShortcut, channelShortcut, gridShortName);
        return null;
    }


    public SchSchedule buildDefaultSchedule(LocalDate localDate, String networkShortcut, String channelShortcut) {
        CorDayOfWeekEnum corDayOfWeekEnum = corDayOfWeekEnumMap.get(localDate.getDayOfWeek());
        SchGrid schGrid = this.schGridService.findOneByNetworkShortcutAndChannelShortcutAndDefaultGridAndDayOfWeek(networkShortcut, channelShortcut, true, corDayOfWeekEnum);
        if (schGrid != null) {
            if (schGrid.getClocks() != null || !schGrid.getClocks().isEmpty()) {
                SchPlaylist schPlaylist = schPlaylistService.saveSchedule(new SchPlaylist().channel(schGrid.getChannel()).network(schGrid.getNetwork()).date(localDate));
                return buildScheduleFromGrid(schGrid, schPlaylist);
            }
            return new SchSchedule().date(localDate).network(schGrid.getNetwork()).channel(schGrid.getChannel());
        }
        throw new BadRequestException("There is no grid for this day");
    }

    private SchSchedule buildScheduleFromGrid(SchGrid schGrid, SchPlaylist schPlaylist) {
        Set<SchEvent> importEvents = getImportLogEventFlatList(schGrid.getClocks());
        if (importEvents != null) {
            Set<SchLogConfiguration> uniqLogsConfigurations = importEvents.stream().map(SchEvent::getSchLogConfiguration).distinct().collect(Collectors.toSet());
            Set<SchLog> scheduleLogs = uniqLogsConfigurations.stream().map(logConfiguration -> this.schLogService.findSchLogForNetworkAndChannelAndDateAndExtension(schGrid.getNetwork().getShortcut(), schGrid.getChannel().getShortcut(), schPlaylist.getDate(), logConfiguration.getExtension())).collect(toSet());
            scheduleLogs.stream().forEach(schLog -> {
                List<SchEmission> schEmissionSet = Lists.newArrayList();
                try {
                    schEmissionSet = schParseLogService.parseLog(schLog);
                } catch (Exception e) {
                    log.error("Wrong log configuration or log doesn't exist");
                }

                //TODO: Import Maksymalnej liczby elementów do godziny
                Set<SchEvent> schEvents = importEvents.stream().filter(schEvent -> schEvent.getSchLogConfiguration().getExtension().equals(schLog.getSchLogConfiguration().getExtension())).collect(toSet());
                Set<SchEvent> filledEvnts = fillEventWithEmissions(schEvents, schEmissionSet);
                schGrid.clocks(fillClockWithEvents(schGrid.getClocks(), filledEvnts));
            });
        }
        return new SchSchedule().date(schPlaylist.getDate()).clocks(schClockBuilder.buildClocks(schGrid.getClocks())).network(schGrid.getNetwork()).channel(schGrid.getChannel());
    }


    private Set<SchEvent> fillEventWithEmissions(Set<SchEvent> schEvents, List<SchEmission> schEmissions) {
        return null;
    }


    private Set<SchEvent> getImportLogEventFlatList(Set<SchClockConfiguration> schClockConfigurationSet) {
        Set<SchEvent> schEvents = Sets.newHashSet();
        schClockConfigurationSet.forEach(schClockConfiguration -> {
            schEvents.addAll(getImportEvents(schClockConfiguration.getEvents()));
        });
        return schEvents;
    }


    public Set<SchEvent> getImportEvents(Set<SchEvent> blocks) {
        Set<SchEvent> schEventsImports = new HashSet<>();
        return blocks.stream().filter(schBlock -> {
            if (!schBlock.getBlocks().isEmpty()) {
                schEventsImports.addAll(this.getImportEvents(schBlock.getBlocks()));
            }
            return schBlock.getEventType().equals(EventTypeEnum.ET_IMPORT_LOG);
        }).collect(toSet());
    }


    private Set<SchClockConfiguration> fillClockWithEvents(Set<SchClockConfiguration> clockConfigurationSet, Set<SchEvent> eventSet) {
        return null;
    }

}
