package io.protone.scheduler.api.dto;

import io.protone.core.api.dto.CorDictionaryDTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Clock.
 */
public class SchClockConfigurationDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String name;

    private String shortName;

    private SchQueueParamsDTO queueParams;

    private CorDictionaryDTO clockCategory;

    private SchTimeParamsDTO timeParams;

    private Set<SchEventDTO> events = new HashSet<>();

    private Set<SchEmissionConfigurationDTO> emissions = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public SchClockConfigurationDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchClockConfigurationDTO name(String name) {
        this.name = name;
        return this;
    }


    public SchQueueParamsDTO getQueueParams() {
        return queueParams;
    }

    public void setQueueParams(SchQueueParamsDTO queueParams) {
        this.queueParams = queueParams;
    }

    public SchClockConfigurationDTO queueParams(SchQueueParamsDTO queueParams) {
        this.queueParams = queueParams;
        return this;
    }

    public SchTimeParamsDTO getTimeParams() {
        return timeParams;
    }

    public void setTimeParams(SchTimeParamsDTO timeParams) {
        this.timeParams = timeParams;
    }

    public SchClockConfigurationDTO timeParams(SchTimeParamsDTO timeParams) {
        this.timeParams = timeParams;
        return this;
    }

    public Set<SchEventDTO> getEvents() {
        return events;
    }

    public void setEvents(Set<SchEventDTO> schEventDTOs) {
        this.events = schEventDTOs;
    }

    public SchClockConfigurationDTO events(Set<SchEventDTO> schEventDTOS) {
        this.events = schEventDTOS;
        return this;
    }

    public SchClockConfigurationDTO addBlock(SchEventDTO schEventDTO) {
        this.events.add(schEventDTO);
        return this;
    }

    public SchClockConfigurationDTO removeBlock(SchEventDTO schEventDTO) {
        this.events.remove(schEventDTO);
        return this;
    }

    public Set<SchEmissionConfigurationDTO> getEmissions() {
        return emissions;
    }

    public void setEmissions(Set<SchEmissionConfigurationDTO> emissions) {
        this.emissions = emissions;
    }

    public SchClockConfigurationDTO emissions(Set<SchEmissionConfigurationDTO> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchClockConfigurationDTO addEmission(SchEmissionConfigurationDTO emission) {
        this.emissions.add(emission);
        return this;
    }

    public SchClockConfigurationDTO removeEmission(SchEmissionConfigurationDTO emission) {
        this.emissions.remove(emission);
        return this;
    }

    public CorDictionaryDTO getClockCategory() {
        return clockCategory;
    }

    public void setClockCategory(CorDictionaryDTO clockCategory) {
        this.clockCategory = clockCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchClockConfigurationDTO clock = (SchClockConfigurationDTO) o;
        if (clock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


    @Override
    public String toString() {
        return "SchClockConfigurationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", queueParams=" + queueParams +
                ", clockCategory=" + clockCategory +
                ", timeParams=" + timeParams +
                ", events=" + events +
                ", emissions=" + emissions +
                '}';
    }
}
