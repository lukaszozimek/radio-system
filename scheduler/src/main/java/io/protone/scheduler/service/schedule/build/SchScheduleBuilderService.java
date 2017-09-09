package io.protone.scheduler.service.schedule.build;

import com.google.common.collect.Lists;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.*;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import io.protone.scheduler.service.*;
import io.protone.scheduler.service.schedule.factory.SchClockBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
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

    @Transactional
    public SchSchedule buildScheduleForDate(LocalDate localDate, String gridShortName, String networkShortcut, String channelShortcut) throws Exception {
        SchGrid schGrid = this.schGridService.findSchGridForNetworkAndChannelAndShortName(networkShortcut, channelShortcut, gridShortName);
        return build(schGrid, localDate);
    }

    @Transactional
    public SchSchedule buildDefaultSchedule(LocalDate localDate, String networkShortcut, String channelShortcut) {
        CorDayOfWeekEnum corDayOfWeekEnum = corDayOfWeekEnumMap.get(localDate.getDayOfWeek());
        SchGrid schGrid = this.schGridService.findOneByNetworkShortcutAndChannelShortcutAndDefaultGridAndDayOfWeek(networkShortcut, channelShortcut, true, corDayOfWeekEnum);
        return build(schGrid, localDate);
    }

    private SchSchedule build(SchGrid schGrid, LocalDate localDate) {
        if (schGrid != null) {
            if (schGrid.getClocks() != null || !schGrid.getClocks().isEmpty()) {
                schGrid.setInternalClockcs(schGrid.getClocks().stream().map(SchGridClockConfiguration::getSchClockConfiguration).collect(toSet()));
                SchPlaylist schPlaylist = schPlaylistService.saveSchPlaylist(new SchPlaylist().channel(schGrid.getChannel()).network(schGrid.getNetwork()).date(localDate));
                return buildScheduleFromGrid(schGrid, schPlaylist);
            }
            return new SchSchedule().date(localDate).network(schGrid.getNetwork()).channel(schGrid.getChannel());
        }
        throw new BadRequestException("There is no grid for this");
    }

    private SchSchedule buildScheduleFromGrid(SchGrid schGrid, SchPlaylist schPlaylist) {
        List<SchEvent> importEvents = getImportLogEventFlatList(schGrid.getInternalClockcs());
        if (importEvents != null) {
            Set<SchLogConfiguration> uniqLogsConfigurations = importEvents.stream().map(SchEvent::getSchLogConfiguration).distinct().collect(Collectors.toSet());
            Set<SchLog> scheduleLogs = uniqLogsConfigurations.stream().map(logConfiguration -> this.schLogService.findSchLogForNetworkAndChannelAndDateAndExtension(schGrid.getNetwork().getShortcut(), schGrid.getChannel().getShortcut(), schPlaylist.getDate(), logConfiguration.getExtension())).collect(toSet());
            scheduleLogs.stream().forEach((SchLog schLog) -> {
                List<SchEmission> schEmissionSet = Lists.newArrayList();
                try {
                    schEmissionSet = schParseLogService.parseLog(schLog);
                } catch (Exception e) {
                    log.error("Wrong log configuration or log doesn't exist");

                }
                if (schEmissionSet != null && !schEmissionSet.isEmpty()) {
                    List<SchEvent> schEvents = importEvents.stream().filter(schEvent -> schEvent.getSchLogConfiguration().getExtension().equals(schLog.getSchLogConfiguration().getExtension())).collect(toList());
                    List<SchEvent> filledEvnts = fillEventWithEmissions(schEvents, schEmissionSet);
                    schGrid.internalClockcs(fillClockWithEvents(schGrid.getInternalClockcs(), filledEvnts));
                }
            });
        }
        return new SchSchedule().date(schPlaylist.getDate()).clocks(schClockBuilder.buildClocks(schGrid.getInternalClockcs())).network(schGrid.getNetwork()).channel(schGrid.getChannel());
    }


    private List<SchEvent> fillEventWithEmissions(List<SchEvent> schEvents, List<SchEmission> schEmissions) {
        int i = 0;
        for (SchEmission schEmission : schEmissions) {
            if (schEvents.size() > i) {
                Long logEmissionsLenght = schEvents.get(i).getEmissionsLog().stream().mapToLong(schEmission1 -> schEmission1.getMediaItem().getLength().longValue()).sum();
                if (logEmissionsLenght < schEvents.get(i).getLength()) {
                    if (schEvents.get(i).getEmissionsLog().isEmpty()) {
                        schEvents.get(i).addEmission(schEmission.seq(1L));
                    } else {
                        schEvents.get(i).addEmission(schEmission.seq(schEvents.get(i)
                                .getEmissionsLog()
                                .stream()
                                .max(comparing(SchEmission::getSequence))
                                .get().getSequence() + 1));
                    }
                    log.debug("put emission in to Block {}", schEmission);
                } else {
                    log.debug("Block is full skip to next one");
                    i++;
                }
            } else {
                log.debug("There is no more import events. Finish parsing");
                break;
            }
        }
        return schEvents;
    }


    private List<SchEvent> getImportLogEventFlatList(Set<SchClockConfiguration> schClockConfigurationSet) {
        List<SchEvent> schEvents = Lists.newArrayList();
        schClockConfigurationSet.stream().sorted(comparing(SchClockConfiguration::getSequence)).forEach(schClockConfiguration -> {
            schEvents.addAll(getImportEvents(schClockConfiguration.getEvents()));
        });
        return schEvents;
    }


    public List<SchEvent> getImportEvents(Set<SchEvent> blocks) {
        List<SchEvent> events = Lists.newArrayList();
        if (blocks != null) {
            events.addAll(blocks.stream().sorted(comparing(SchEvent::getSequence)).filter(schBlock -> {
                if (!schBlock.getBlocks().isEmpty()) {
                    events.addAll(this.getImportEvents(schBlock.getBlocks()));
                }
                return schBlock.getEventType().equals(EventTypeEnum.ET_IMPORT_LOG);
            }).collect(toList()));
            return events;
        }
        return Lists.newArrayList();
    }

    private Set<SchClockConfiguration> fillClockWithEvents(Set<SchClockConfiguration> clockConfigurationSet, List<SchEvent> schEvents) {
        schEvents.stream().forEach(schEvent -> {
            clockConfigurationSet.stream().forEach(schClockConfiguration -> {
                updateEventsRecusiveOnClockLevel(schClockConfiguration, schEvent);
            });
        });
        return clockConfigurationSet;
    }

    public void updateEventsRecusiveOnClockLevel(SchClockConfiguration schClockConfiguration, SchEvent schEvent) {
        Optional<SchEvent> eventOnClockLevel = schClockConfiguration.getEvents().stream().filter(schEvent1 -> schEvent1.getId().equals(schEvent.getId())).findFirst();
        if (eventOnClockLevel.isPresent()) {
            log.debug("Found import event on clock level");
            schClockConfiguration.getEvents().remove(eventOnClockLevel);
            schClockConfiguration.getEvents().add(schEvent);
        } else {
            log.debug("Start Searching event Recursive in each event");
            schClockConfiguration.getEvents().stream().map(schEvent1 -> updateNestedEvenRecusive(schEvent1, schEvent)).collect(toSet());
        }
    }

    public SchEvent updateNestedEvenRecusive(SchEvent eventClock, SchEvent schEventFilled) {
        Optional<SchEvent> eventOnClockLevel = eventClock.getBlocks().stream().filter(schEvent1 -> schEvent1.getId().equals(schEventFilled.getId())).findFirst();
        if (eventOnClockLevel.isPresent()) {
            eventClock.getBlocks().remove(eventOnClockLevel);
            eventClock.getBlocks().add(schEventFilled);
        } else {
            return eventClock.blocks(eventClock.getBlocks().stream().map(localevent -> updateNestedEvenRecusive(localevent, schEventFilled)).collect(toSet()));
        }
        return eventClock;

    }


}
