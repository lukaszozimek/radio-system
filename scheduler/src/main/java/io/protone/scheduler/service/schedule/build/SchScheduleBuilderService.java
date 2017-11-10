package io.protone.scheduler.service.schedule.build;

import com.google.common.collect.Lists;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.library.service.LibFileItemService;
import io.protone.library.service.LibMediaItemService;
import io.protone.scheduler.domain.*;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import io.protone.scheduler.service.*;
import io.protone.scheduler.service.schedule.factory.SchClockBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Inject
    private LibMediaItemService libMediaItemService;
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
        return build(schGrid, localDate);
    }


    public SchSchedule buildDefaultSchedule(LocalDate localDate, String networkShortcut, String channelShortcut) {
        CorDayOfWeekEnum corDayOfWeekEnum = corDayOfWeekEnumMap.get(localDate.getDayOfWeek());
        SchGrid schGrid = this.schGridService.findOneByNetworkShortcutAndChannelShortcutAndDefaultGridAndDayOfWeek(networkShortcut, channelShortcut, true, corDayOfWeekEnum);
        return build(schGrid, localDate);
    }

    private SchSchedule build(SchGrid schGrid, LocalDate localDate) {
        if (schGrid != null) {
            if (schGrid.getChilds() != null || !schGrid.getChilds().isEmpty()) {
                schGrid.setInternalClockcs(schGrid.getSchEventTemplates().stream().map(schClock -> {
                    schClock.getChild().sequence(schClock.getSequence());
                    return (SchClockTemplate) schClock.getChild();
                }).collect(toList()));
                SchPlaylist schPlaylist = schPlaylistService.saveSchPlaylist(new SchPlaylist().channel(schGrid.getChannel()).network(schGrid.getNetwork()).date(localDate));
                return buildScheduleFromGrid(schGrid, schPlaylist);
            }
            return new SchSchedule().date(localDate).network(schGrid.getNetwork()).channel(schGrid.getChannel());
        }
        throw new BadRequestException("There is no grid for this");
    }

    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    private SchSchedule buildScheduleFromGrid(SchGrid schGrid, SchPlaylist schPlaylist) {
        List<SchEventTemplate> importEvents = getImportLogEventFlatList(schGrid.getInternalClockcs());
        if (importEvents != null) {
            Set<SchLogConfiguration> uniqLogsConfigurations = importEvents.stream().map(SchEventTemplate::getSchLogConfiguration).distinct().collect(Collectors.toSet());
            Set<SchLog> scheduleLogs = uniqLogsConfigurations.stream().map(logConfiguration -> this.schLogService.findSchLogForNetworkAndChannelAndDateAndExtension(schGrid.getNetwork().getShortcut(), schGrid.getChannel().getShortcut(), schPlaylist.getDate(), logConfiguration.getExtension())).collect(toSet());
            scheduleLogs.stream().forEach((SchLog schLog) -> {
                List<SchEmission> schEmissionSet = Lists.newArrayList();
                try {
                    schEmissionSet = schParseLogService.parseLog(schLog);
                } catch (Exception e) {
                    log.error("Wrong log configuration or log doesn't exist");

                }
                if (schEmissionSet != null && !schEmissionSet.isEmpty()) {
                    List<SchEventTemplate> schEventTemplates = importEvents.stream().filter(schEvent -> schEvent.getSchLogConfiguration().getExtension().equals(schLog.getSchLogConfiguration().getExtension())).collect(toList());
                    List<SchEventTemplate> filledEvnts = fillEventWithEmissions(schEventTemplates, schEmissionSet, schPlaylist);
                    schGrid.internalClockcs(fillClockWithEvents(schGrid.getInternalClockcs(), filledEvnts));
                }
            });
        }
        return new SchSchedule().date(schPlaylist.getDate()).clocks(schClockBuilder.buildClocks(schGrid.getInternalClockcs(), LocalDateTime.of(schPlaylist.getDate(), LocalTime.of(0, 0, 0)), schPlaylist)).network(schGrid.getNetwork()).channel(schGrid.getChannel());
    }


    private List<SchEventTemplate> fillEventWithEmissions(List<SchEventTemplate> schEventTemplates, List<SchEmission> schEmissions, SchPlaylist schPlaylist) {
        int i = 0;
        for (SchEmission schEmission : schEmissions) {
            if (schEventTemplates.size() > i) {
                Long logEmissionsLenght = schEventTemplates.get(i).getEmissionsLog().stream().mapToLong(schEmission1 -> schEmission1.getMediaItem().getLength().longValue()).sum();
                if (logEmissionsLenght < schEventTemplates.get(i).getLength()) {
                    if (schEventTemplates.get(i).getEmissionsLog().isEmpty()) {
                        schEventTemplates.get(i).addEmission(schEmission.seq(1L).playlist(null).network(schEventTemplates.get(i).getNetwork()).channel(schEventTemplates.get(i).getChannel()));
                    } else {
                        schEventTemplates.get(i).
                                addEmission(schEmission.seq(schEventTemplates.get(i)
                                        .getEmissionsLog()
                                        .stream()
                                        .max(comparing(SchEmission::getSequence))
                                        .get().getSequence() + 1)
                                        .playlist(null)
                                        .network(schEventTemplates.get(i).getNetwork())
                                        .channel(schEventTemplates.get(i).getChannel()));
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
        return schEventTemplates;
    }


    private List<SchEventTemplate> getImportLogEventFlatList(List<SchClockTemplate> schClockTemplateSet) {
        List<SchEventTemplate> schEventTemplates = Lists.newArrayList();
        schClockTemplateSet.stream().sorted(comparing(SchClockTemplate::getSequence)).forEach(schClockConfiguration -> {
            schEventTemplates.addAll(getImportEvents(schClockConfiguration.getChilds()));
        });
        return schEventTemplates;
    }


    public List<SchEventTemplate> getImportEvents(List<SchEventTemplate> blocks) {
        List<SchEventTemplate> events = Lists.newArrayList();
        if (blocks != null) {
            events.addAll(blocks.stream().sorted(comparing(SchEventTemplate::getSequence)).filter(schBlock -> {
                if (!schBlock.getSchEventTemplates().isEmpty()) {
                    events.addAll(this.getImportEvents(schBlock.getChilds()));
                }
                return schBlock.getEventType().equals(EventTypeEnum.ET_IMPORT_LOG);
            }).collect(toList()));
            return events;
        }
        return Lists.newArrayList();
    }

    private List<SchClockTemplate> fillClockWithEvents(List<SchClockTemplate> clockConfigurationSet, List<SchEventTemplate> schEventTemplates) {
        schEventTemplates.stream().forEach(schEvent -> {
            clockConfigurationSet.stream().forEach(schClockConfiguration -> {
                updateEventsRecusiveOnClockLevel(schClockConfiguration, schEvent);
            });
        });
        return clockConfigurationSet;
    }

    public void updateEventsRecusiveOnClockLevel(SchClockTemplate schClockTemplate, SchEventTemplate schEventTemplate) {
        Optional<SchEventTemplate> eventOnClockLevel = schClockTemplate.getChilds().stream().filter(schEvent1 -> schEvent1.getId().equals(schEventTemplate.getId())).findFirst();
        if (eventOnClockLevel.isPresent()) {
            log.debug("Found import event on clock level");
            schClockTemplate.getChilds().remove(eventOnClockLevel);
            schClockTemplate.getChilds().add(schEventTemplate);
        } else {
            log.debug("Start Searching event Recursive in each event");
            schClockTemplate.getChilds().stream().map(schEvent1 -> updateNestedEvenRecusive(schEvent1, schEventTemplate)).collect(toSet());
        }
    }

    public SchEventTemplate updateNestedEvenRecusive(SchEventTemplate eventClock, SchEventTemplate schEventTemplateFilled) {
        Optional<SchEventTemplate> eventOnClockLevel = eventClock.getChilds().stream().filter(schEvent1 -> schEvent1.getId().equals(schEventTemplateFilled.getId())).findFirst();
        if (eventOnClockLevel.isPresent()) {
            eventClock.getChilds().remove(eventOnClockLevel);
            eventClock.getChilds().add(schEventTemplateFilled);
        } else {
            return eventClock.schEventTemplates(eventClock.getSchEventTemplates().stream().map(localevent -> {
                updateNestedEvenRecusive(localevent.getChild(), schEventTemplateFilled);
                return localevent;
            }).collect(toList()));
        }
        return eventClock;

    }


}
