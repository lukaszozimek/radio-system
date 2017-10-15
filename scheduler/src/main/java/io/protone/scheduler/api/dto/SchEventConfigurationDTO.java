package io.protone.scheduler.api.dto;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.scheduler.domain.SchEventConfiguration;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Block.
 */
public class SchEventConfigurationDTO extends SchConfigurationTimeParamsDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String name;

    private String shortName;

    private CorDictionaryDTO eventCategory;

    private Set<SchEmissionConfigurationDTO> emissions = new HashSet<>();

    private EventTypeEnum eventType;

    private SchLogConfigurationDTO schLogConfiguration;

    private Set<SchEventDTO> events = new HashSet<>();

    private Set<SchEventConfiguration> schEventConfigurations = new HashSet<>();


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

    public SchEventConfigurationDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchEventConfigurationDTO name(String name) {
        this.name = name;
        return this;
    }

    public Set<SchEmissionConfigurationDTO> getEmissions() {
        return emissions;
    }

    public void setEmissions(Set<SchEmissionConfigurationDTO> emissions) {
        this.emissions = emissions;
    }

    public SchEventConfigurationDTO emissions(Set<SchEmissionConfigurationDTO> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchEventConfigurationDTO addEmission(SchEmissionConfigurationDTO emission) {
        this.emissions.add(emission);
        return this;
    }

    public SchEventConfigurationDTO removeEmission(SchEmissionConfigurationDTO emission) {
        this.emissions.remove(emission);
        return this;
    }

    public EventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public SchEventConfigurationDTO eventType(EventTypeEnum eventType) {
        this.eventType = eventType;
        return this;
    }


    public CorDictionaryDTO getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(CorDictionaryDTO eventCategory) {
        this.eventCategory = eventCategory;
    }

    public SchEventConfigurationDTO eventCategory(CorDictionaryDTO eventCategory) {
        this.eventCategory = eventCategory;
        return this;
    }

    public SchLogConfigurationDTO getSchLogConfiguration() {
        return schLogConfiguration;
    }

    public void setSchLogConfiguration(SchLogConfigurationDTO schLogConfiguration) {
        this.schLogConfiguration = schLogConfiguration;
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

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Set<SchEventDTO> getEvents() {
        return events;
    }

    public void setEvents(Set<SchEventDTO> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchEventConfigurationDTO block = (SchEventConfigurationDTO) o;
        if (block.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), block.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


    @Override
    public String toString() {
        return "SchEventConfigurationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", eventCategory=" + eventCategory +
                ", emissions=" + emissions +
                ", eventType=" + eventType +
                ", schLogConfiguration=" + schLogConfiguration +
                ", sequence=" + sequence +

                '}';
    }


    public Set<SchEventConfiguration> getSchEventConfigurations() {
        return schEventConfigurations;
    }

    public void setSchEventConfigurations(Set<SchEventConfiguration> schEventConfigurations) {
        this.schEventConfigurations = schEventConfigurations;
    }
}