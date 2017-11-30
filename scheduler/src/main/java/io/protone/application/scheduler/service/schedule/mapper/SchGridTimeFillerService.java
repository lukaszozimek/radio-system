package io.protone.application.scheduler.service.schedule.mapper;

import io.protone.scheduler.domain.*;
import org.hibernate.internal.util.SerializationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static io.protone.scheduler.domain.enumeration.EventTypeEnum.ET_IMPORT_LOG;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 05.09.2017.
 */

@Service
public class SchGridTimeFillerService {
    private final Logger log = LoggerFactory.getLogger(SchGridTimeFillerService.class);

    public List<SchClockTemplate> fillPredictedStartTimes(List<SchClockTemplate> internalClockcs, LocalDateTime localDateTime) {

        for (int i = 0; i < internalClockcs.size(); i++) {
            Map<Long, SchTimeParams> schTimeParamsMap = internalClockcs.get(i).getChildsTimeParams();
            schTimeParamsMap.putAll(internalClockcs.get(i).getEmissionsLogMap());
            if (i == 0) {
                int finalI = i;
                schTimeParamsMap.keySet().stream().sorted().forEach(sequence -> {
                    if (sequence == 0) {
                        if (schTimeParamsMap.get(sequence) instanceof SchEventTemplate) {
                            internalClockcs.get(finalI).setStartTime(localDateTime);
                            internalClockcs.get(finalI).setEndTime(fillEvent((SchEventTemplate) schTimeParamsMap.get(sequence), localDateTime).getEndTime());
                        }
                        if (schTimeParamsMap.get(sequence) instanceof SchEmissionTemplate) {
                            internalClockcs.get(finalI).setStartTime(localDateTime);
                            SchEmissionTemplate schEmissionTemplate = (SchEmissionTemplate) schTimeParamsMap.get(sequence).startTime(localDateTime);
                            schEmissionTemplate.endTime(schEmissionTemplate.getStartTime().plusSeconds(schEmissionTemplate.getMediaItem().getLength().longValue() / 1000));
                            internalClockcs.get(finalI).setEndTime(schEmissionTemplate.getEndTime());
                        }
                    } else {
                        if (schTimeParamsMap.get(sequence) instanceof SchEventTemplate) {
                            internalClockcs.get(finalI).setEndTime(fillEvent((SchEventTemplate) schTimeParamsMap.get(sequence), schTimeParamsMap.get(sequence - 1).getEndTime()).getEndTime());
                        }
                        if (schTimeParamsMap.get(sequence) instanceof SchEmissionTemplate) {
                            SchEmissionTemplate schEmissionTemplate = (SchEmissionTemplate) schTimeParamsMap.get(sequence).startTime(schTimeParamsMap.get(sequence - 1).getEndTime());
                            schEmissionTemplate.endTime(schEmissionTemplate.getStartTime().plusSeconds(schEmissionTemplate.getMediaItem().getLength().longValue() / 1000));
                            internalClockcs.get(finalI).setEndTime(schEmissionTemplate.getEndTime());
                        }
                    }

                });
            } else {
                int finalI1 = i;
                schTimeParamsMap.keySet().stream().sorted().forEach(sequence -> {
                    if (sequence == 0) {
                        internalClockcs.get(finalI1).setStartTime(internalClockcs.get(finalI1 - 1).getEndTime());

                        if (schTimeParamsMap.get(sequence) instanceof SchEventTemplate) {
                            SchEventTemplate eventTemplate =
                                    fillEvent((SchEventTemplate) schTimeParamsMap.get(sequence), internalClockcs.get(finalI1 - 1).getEndTime());
                            internalClockcs.get(finalI1).setEndTime(eventTemplate.getEndTime());
                        }
                        if (schTimeParamsMap.get(sequence) instanceof SchEmissionTemplate) {
                            internalClockcs.get(finalI1).setStartTime(internalClockcs.get(finalI1 - 1).getEndTime());
                            SchEmissionTemplate schEmissionTemplate = (SchEmissionTemplate) schTimeParamsMap.get(sequence).startTime(internalClockcs.get(finalI1 - 1).getEndTime());
                            schEmissionTemplate.endTime(schEmissionTemplate.getStartTime().plusSeconds(schEmissionTemplate.getMediaItem().getLength().longValue() / 1000));
                            internalClockcs.get(finalI1).setEndTime(schEmissionTemplate.getEndTime());
                        }
                    } else {
                        if (schTimeParamsMap.get(sequence) instanceof SchEventTemplate) {
                            SchEventTemplate eventTemplate = fillEvent((SchEventTemplate) schTimeParamsMap.get(sequence), schTimeParamsMap.get(sequence - 1).getEndTime());
                            internalClockcs.get(finalI1).setEndTime(eventTemplate.getEndTime());
                        }
                        if (schTimeParamsMap.get(sequence) instanceof SchEmissionTemplate) {
                            SchEmissionTemplate schEmissionTemplate = (SchEmissionTemplate) schTimeParamsMap.get(sequence).startTime(schTimeParamsMap.get(sequence - 1).getEndTime());
                            schEmissionTemplate.endTime(schEmissionTemplate.getStartTime().plusSeconds(schEmissionTemplate.getMediaItem().getLength().longValue() / 1000));
                            internalClockcs.get(finalI1).setEndTime(schEmissionTemplate.getEndTime());
                        }
                    }


                });

            }
            i++;
        }
        return internalClockcs;
    }


    public List<SchClockTemplate> cloneClockStructureFromHibernate(SchGrid schGrid) {

        return schGrid.getSchEventTemplates().stream().map(schClock -> {
            schClock.getChild().sequence(schClock.getSequence());
            schClock.getChild().getSchEventTemplates().stream().forEach(schEventTemplateEvnetTemplate -> schEventTemplateEvnetTemplate.child((SchEventTemplate) SerializationHelper.clone(cloneEventStructreFromHibernate(schEventTemplateEvnetTemplate))));
            return (SchClockTemplate) SerializationHelper.clone(schClock.getChild());
        }).collect(toList());
    }

    public List<SchEventTemplate> fillEventWithEmissions(List<SchEventTemplate> schEventTemplates, List<SchEmission> schEmissions, SchPlaylist schPlaylist) {
        int i = 0;
        for (int y = 0; y < schEmissions.size(); y++) {
            if (schEventTemplates.size() > i) {
                Long logEmissionsLenght = schEventTemplates.get(i).getEmissionsLog().stream().mapToLong(schEmission1 -> schEmission1.getMediaItem().getLength().longValue()).sum();
                if (logEmissionsLenght < schEventTemplates.get(i).getLength()) {
                    if (schEventTemplates.get(i).getEmissionsLog().isEmpty()) {
                        if (isMatchingToThisBlock(schEmissions.get(y), schEventTemplates.get(i))) {
                            schEventTemplates.get(i).addEmission(schEmissions.get(y).sequence(0L).playlist(schPlaylist).network(schEventTemplates.get(i).getNetwork()).channel(schEventTemplates.get(i).getChannel()));
                        }

                    } else {
                        if (isMatchingToThisBlock(schEmissions.get(y), schEventTemplates.get(i))) {
                            schEventTemplates.get(i).
                                    addEmission(schEmissions.get(y)
                                            .endTime(schEmissions.get(y).getStartTime().plusSeconds(schEmissions.get(y).getMediaItem().getLength().longValue() / 1000)).sequence(schEventTemplates.get(i)
                                                    .getEmissionsLog()
                                                    .stream()
                                                    .max(comparing(SchEmission::getSequence))
                                                    .get().getSequence() + 1)
                                            .playlist(schPlaylist)
                                            .network(schEventTemplates.get(i).getNetwork())
                                            .channel(schEventTemplates.get(i).getChannel()));
                        }
                    }
                    log.debug("put emission in to Block {}", schEmissions.get(y));
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

    private boolean isMatchingToThisBlock(SchEmission schEmission, SchEventTemplate schEventTemplate) {
        return schEmission.getStartTime().getHour() == schEventTemplate.getStartTime().getHour() && schEmission.getStartTime().getMinute() > schEventTemplate.getStartTime().getMinute();
    }

    private static SchEventTemplate cloneEventStructreFromHibernate(SchEventTemplateEvnetTemplate schEventTemplateEvnetTemplate) {
        if (schEventTemplateEvnetTemplate.getChild().getSchEventTemplates() != null && !schEventTemplateEvnetTemplate.getChild().getSchEventTemplates().isEmpty()) {
            schEventTemplateEvnetTemplate.getChild().schEventTemplates(schEventTemplateEvnetTemplate.getChild()
                    .getSchEventTemplates().stream()
                    .map(schEventTemplateEvnetTemplate1 -> schEventTemplateEvnetTemplate1.child((SchEventTemplate) SerializationHelper.clone(cloneEventStructreFromHibernate(schEventTemplateEvnetTemplate1)))
                            .sequence(schEventTemplateEvnetTemplate1.getSequence())).collect(toList()));
        }
        return schEventTemplateEvnetTemplate.getChild().sequence(schEventTemplateEvnetTemplate.getSequence());
    }

    private static SchEventTemplate prefilEventTemplate(SchEventTemplate schEventTemplate) {
        Map<Long, SchTimeParams> schTimeParamsMap = schEventTemplate.getChildsTimeParams();
        schTimeParamsMap.putAll(schEventTemplate.getChildsTimeParams());
        schTimeParamsMap.keySet().stream().forEach(sequence -> {
            if (sequence == 0) {
                if (schTimeParamsMap.get(sequence) instanceof SchEventTemplate) {
                    schEventTemplate.setEndTime(fillEvent((SchEventTemplate) schTimeParamsMap.get(sequence), schEventTemplate.getStartTime()).getEndTime());
                }
                if (schTimeParamsMap.get(sequence) instanceof SchEmissionTemplate) {
                    schTimeParamsMap.get(sequence).setStartTime(schEventTemplate.getStartTime());
                    SchEmissionTemplate schEmissionTemplate = (SchEmissionTemplate) schTimeParamsMap.get(sequence);
                    schTimeParamsMap.get(sequence).setEndTime(schEmissionTemplate.getStartTime().plusSeconds(schEmissionTemplate.getMediaItem().getLength().longValue() / 1000));
                    schEmissionTemplate.setEndTime(schTimeParamsMap.get(sequence).getEndTime());
                }
            } else {
                if (schTimeParamsMap.get(sequence) instanceof SchEventTemplate) {

                    schEventTemplate.setEndTime(fillEvent((SchEventTemplate) schTimeParamsMap.get(sequence), schTimeParamsMap.get(sequence - 1).getEndTime()).getEndTime());
                }
                if (schTimeParamsMap.get(sequence) instanceof SchEmissionTemplate) {
                    schTimeParamsMap.get(sequence).setStartTime(schTimeParamsMap.get(sequence - 1).getEndTime());
                    SchEmissionTemplate schEmissionTemplate = (SchEmissionTemplate) schTimeParamsMap.get(sequence);
                    schTimeParamsMap.get(sequence).setEndTime(schEmissionTemplate.getStartTime().plusSeconds(schEmissionTemplate.getMediaItem().getLength().longValue() / 1000));
                    schEmissionTemplate.setEndTime(schTimeParamsMap.get(sequence).getEndTime());
                }
            }

        });
        return schEventTemplate;
    }

    private static SchEventTemplate fillEvent(SchEventTemplate schEventTemplate, LocalDateTime startTime) {
        schEventTemplate.startTime(startTime);
        if (ET_IMPORT_LOG.equals(schEventTemplate.getEventType())) {
            schEventTemplate.endTime(schEventTemplate.getStartTime().plusSeconds(schEventTemplate.getLength().longValue() / 1000));

        } else {
            schEventTemplate = prefilEventTemplate(schEventTemplate);
        }
        return schEventTemplate;

    }
}
