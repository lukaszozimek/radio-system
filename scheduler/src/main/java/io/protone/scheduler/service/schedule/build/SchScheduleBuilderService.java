package io.protone.scheduler.service.schedule.build;

import com.google.common.collect.Lists;
import io.protone.application.scheduler.service.schedule.mapper.SchGridTimeFillerService;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.library.service.LibFileItemService;
import io.protone.library.service.LibMediaItemService;
import io.protone.scheduler.domain.*;
import io.protone.scheduler.service.*;
import io.protone.scheduler.service.schedule.factory.SchClockBuilder;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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

import static io.protone.scheduler.domain.enumeration.EventTypeEnum.ET_IMPORT_LOG;
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

    @Inject
    private SchGridTimeFillerService schGridTimeFillerService;

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
    public SchSchedule buildScheduleForDate(LocalDate localDate, String gridShortName, String organizationShortcut, String channelShortcut) throws Exception {
        SchGrid schGrid = this.schGridService.findSchGridForNetworkAndChannelAndShortName(organizationShortcut, channelShortcut, gridShortName);
        return build(schGrid, localDate);
    }

    @Transactional
    public SchSchedule buildDefaultSchedule(LocalDate localDate, String organizationShortcut, String channelShortcut) {
        CorDayOfWeekEnum corDayOfWeekEnum = corDayOfWeekEnumMap.get(localDate.getDayOfWeek());
        SchGrid schGrid = SerializationUtils.clone(this.schGridService.findOneByorganizationShortcutAndChannelShortcutAndDefaultGridAndDayOfWeek(organizationShortcut, channelShortcut, true, corDayOfWeekEnum));
        return build(schGrid, localDate);
    }

    private SchSchedule build(SchGrid schGrid, LocalDate localDate) {
        if (schGrid != null) {
            if (schGrid.getChilds() != null || !schGrid.getChilds().isEmpty()) {
                schGrid.setInternalClockcs(schGridTimeFillerService.cloneClockStructureFromHibernate(schGrid));
                SchPlaylist schPlaylist = schPlaylistService.saveSchPlaylist(new SchPlaylist().channel(schGrid.getChannel()).date(localDate));
                return buildScheduleFromGrid(schGrid, schPlaylist);
            }
            return new SchSchedule().date(localDate).channel(schGrid.getChannel());
        }
        throw new BadRequestException("There is no grid for this");
    }


    @Transactional(noRollbackFor = Exception.class)
    SchSchedule buildScheduleFromGrid(SchGrid schGrid, SchPlaylist schPlaylist) {
        schGrid.setInternalClockcs(schGridTimeFillerService.fillPredictedStartTimes(schGrid.getInternalClockcs(), LocalDateTime.of(schPlaylist.getDate(), LocalTime.of(0, 0, 0))));

        List<SchEventTemplate> importEvents = getImportLogEventFlatList(schGrid.getInternalClockcs());
        if (importEvents != null) {
            Set<SchLogConfiguration> uniqLogsConfigurations = importEvents.stream().map(SchEventTemplate::getSchLogConfiguration).distinct().collect(Collectors.toSet());
            Set<SchLog> scheduleLogs = uniqLogsConfigurations.stream().map(logConfiguration -> this.schLogService.findSchLogForNetworkAndChannelAndDateAndExtension(schGrid.getChannel().getOrganization().getShortcut(), schGrid.getChannel().getShortcut(), schPlaylist.getDate(), logConfiguration.getExtension())).collect(toSet());
            scheduleLogs.stream().forEach((SchLog schLog) -> {
                if (schLog != null) {
                    List<SchEmission> schEmissionSet = Lists.newArrayList();
                    try {
                        schEmissionSet = schParseLogService.parseLog(schLog);
                    } catch (Exception e) {
                        log.error("Wrong log configuration or log doesn't exist");
                    }
                    if (schEmissionSet != null && !schEmissionSet.isEmpty()) {
                        List<SchEventTemplate> schEventTemplates = importEvents.stream().filter(schEvent -> schEvent.getSchLogConfiguration().getExtension().equals(schLog.getSchLogConfiguration().getExtension())).collect(toList());
                        List<SchEventTemplate> filledEvnts = schGridTimeFillerService.fillEventWithEmissions(schEventTemplates, schEmissionSet, schPlaylist);
                        schGrid.internalClockcs(fillClockWithEvents(schGrid.getInternalClockcs(), filledEvnts));
                    }
                }
            });
        }
        SchSchedule schSchedule = new SchSchedule().date(schPlaylist.getDate()).channel(schGrid.getChannel());
        List<SchClock> schClocks = schClockBuilder.buildClocks(schGrid.getInternalClockcs(), LocalDateTime.of(schPlaylist.getDate(), LocalTime.of(0, 0, 0)), schPlaylist);

        for (int i = 0; i < schClocks.size(); i++) {
            schSchedule.addBlock(new SchBlockSchBlock().child(schClocks.get(i)).parent(schSchedule).sequence(schClocks.get(i).getSequence()));
        }
        return schSchedule;
    }


    private List<SchEventTemplate> getImportLogEventFlatList(List<SchClockTemplate> schClockTemplateSet) {
        List<SchEventTemplate> schEventTemplates = Lists.newArrayList();
        schClockTemplateSet.stream().forEach(schClockConfiguration -> {
            schEventTemplates.addAll(getImportEvents(schClockConfiguration.getChilds()));
        });
        return schEventTemplates;
    }


    private List<SchEventTemplate> getImportEvents(List<SchEventTemplate> blocks) {
        List<SchEventTemplate> events = Lists.newArrayList();
        if (blocks != null && !blocks.isEmpty()) {
            events.addAll(blocks.stream().filter(schBlock -> {
                if (!schBlock.getSchEventTemplates().isEmpty()) {
                    events.addAll(this.getImportEvents(schBlock.getChilds()));
                }
                return ET_IMPORT_LOG.equals(schBlock.getEventType());
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

    private void updateEventsRecusiveOnClockLevel(SchClockTemplate schClockTemplate, SchEventTemplate schEventTemplate) {
        Optional<SchEventTemplate> eventOnClockLevel = schClockTemplate.getSchEventTemplates().stream().filter(schEvent1 -> schEvent1.getSequence().equals(schEventTemplate.getSequence())).map(schEventTemplateEvnetTemplate -> schEventTemplateEvnetTemplate.getChild()).findFirst();
        if (eventOnClockLevel.isPresent() && eventOnClockLevel.get().getEmissionsLog().isEmpty()) {
            log.debug("Found import event on clock level");
            schClockTemplate.getChilds().remove(eventOnClockLevel.get());
            schClockTemplate.getChilds().add(schEventTemplate);
        } else {
            log.debug("Start Searching event Recursive in each event");
            schClockTemplate.getChilds().stream().map(schEvent1 -> updateNestedEvenRecusive(schEvent1, schEventTemplate)).collect(toList());
        }
    }

    private SchEventTemplate updateNestedEvenRecusive(SchEventTemplate unfilledEvent, SchEventTemplate filledEvent) {
        Optional<SchEventTemplate> eventOnClockLevel = unfilledEvent.getSchEventTemplates().stream().filter(schEvent1 -> {
            if (!filledEvent.getSchEventTemplates().isEmpty()) {
                return schEvent1.getSequence().equals(filledEvent.getSequence());

            }
            return false;
        }).map(schEventTemplateEvnetTemplate -> schEventTemplateEvnetTemplate.getChild()).findFirst();
        if (eventOnClockLevel.isPresent() && eventOnClockLevel.get().getEmissionsLog().isEmpty()) {
            unfilledEvent.getChilds().remove(eventOnClockLevel.get());
            unfilledEvent.getChilds().add(filledEvent);
        } else {
            return unfilledEvent.schEventTemplates(unfilledEvent.getSchEventTemplates().stream().map(localevent -> {
                updateNestedEvenRecusive(localevent.getChild(), filledEvent);
                return localevent;
            }).collect(toList()));
        }
        return unfilledEvent;

    }


}
