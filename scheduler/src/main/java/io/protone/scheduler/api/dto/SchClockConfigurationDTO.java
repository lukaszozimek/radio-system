package io.protone.scheduler.api.dto;

import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Clock.
 */
public class SchClockConfigurationDTO extends SchConfigurationTimeParamsDTO {

    private static final long serialVersionUID = 1L;

    private String name;

    private String shortName;

    private CorDictionaryDTO clockCategory;

    private Set<SchEventDTO> events = new HashSet<>();

    private Set<SchEventConfigurationDTO> schEventConfigurationDTOS = new HashSet<>();

    private Set<SchEmissionConfigurationDTO> emissions = new HashSet<>();

    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

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

    public CoreUserThinDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CoreUserThinDTO createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public CoreUserThinDTO getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(CoreUserThinDTO lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
                ", sequence=" + sequence +
                ", clockCategory=" + clockCategory +
                ", events=" + events +
                ", emissions=" + emissions +
                '}';
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Set<SchEventConfigurationDTO> getSchEventConfigurationDTOS() {
        return schEventConfigurationDTOS;
    }

    public void setSchEventConfigurationDTOS(Set<SchEventConfigurationDTO> schEventConfigurationDTOS) {
        this.schEventConfigurationDTOS = schEventConfigurationDTOS;
    }
}
