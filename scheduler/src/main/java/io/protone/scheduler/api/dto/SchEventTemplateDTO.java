package io.protone.scheduler.api.dto;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.scheduler.api.dto.base.SchTemplateTimeParamsDTO;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import uk.co.jemos.podam.common.PodamExclude;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Block.
 */
public class SchEventTemplateDTO extends SchTemplateTimeParamsDTO implements Serializable {

    private String name;

    private String shortName;

    private Boolean isInstance;

    @PodamExclude
    private CorDictionaryDTO eventCategory;

    private EventTypeEnum eventType;

    @PodamExclude
    private SchLogConfigurationDTO schLogConfiguration;


    @PodamExclude
    private List<SchEventTemplateDTO> schEventTemplateDTOS = new ArrayList<>();


    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    @PodamExclude
    private List<SchEmissionTemplateDTO> emissions = new ArrayList<>();

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

    public SchEventTemplateDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchEventTemplateDTO name(String name) {
        this.name = name;
        return this;
    }

    public List<SchEmissionTemplateDTO> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<SchEmissionTemplateDTO> emissions) {
        this.emissions = emissions;
    }

    public SchEventTemplateDTO emissions(List<SchEmissionTemplateDTO> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchEventTemplateDTO addEmission(SchEmissionTemplateDTO emission) {
        this.emissions.add(emission);
        return this;
    }

    public SchEventTemplateDTO removeEmission(SchEmissionTemplateDTO emission) {
        this.emissions.remove(emission);
        return this;
    }

    public EventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public SchEventTemplateDTO eventType(EventTypeEnum eventType) {
        this.eventType = eventType;
        return this;
    }


    public CorDictionaryDTO getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(CorDictionaryDTO eventCategory) {
        this.eventCategory = eventCategory;
    }

    public SchEventTemplateDTO eventCategory(CorDictionaryDTO eventCategory) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchEventTemplateDTO block = (SchEventTemplateDTO) o;
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
        return "SchEventTemplateDTO{" +
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


    public List<SchEventTemplateDTO> getSchEventTemplateDTOS() {
        return schEventTemplateDTOS;
    }

    public void setSchEventTemplateDTOS(List<SchEventTemplateDTO> schEventTemplateDTOS) {
        this.schEventTemplateDTOS = schEventTemplateDTOS;
    }

    public SchEventTemplateDTO addEventsConfiguration(SchEventTemplateDTO schEventTemplateDTO) {
        this.schEventTemplateDTOS.add(schEventTemplateDTO);
        return this;
    }

    public Boolean getInstance() {
        return isInstance;
    }

    public void setInstance(Boolean instance) {
        isInstance = instance;
    }

    public SchEventTemplateDTO sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }
}
