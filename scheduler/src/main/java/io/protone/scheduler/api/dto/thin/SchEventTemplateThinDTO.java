package io.protone.scheduler.api.dto.thin;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.scheduler.api.dto.SchLogConfigurationDTO;
import io.protone.scheduler.api.dto.base.SchTemplateTimeParamsDTO;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Block.
 */
public class SchEventTemplateThinDTO extends SchTemplateTimeParamsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String shortName;

    private CorDictionaryDTO eventCategory;

    private EventTypeEnum eventType;

    private SchLogConfigurationDTO schLogConfiguration;


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

    public SchEventTemplateThinDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchEventTemplateThinDTO name(String name) {
        this.name = name;
        return this;
    }


    public EventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public SchEventTemplateThinDTO eventType(EventTypeEnum eventType) {
        this.eventType = eventType;
        return this;
    }

    public CorDictionaryDTO getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(CorDictionaryDTO eventCategory) {
        this.eventCategory = eventCategory;
    }

    public SchEventTemplateThinDTO eventCategory(CorDictionaryDTO eventCategory) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchEventTemplateThinDTO block = (SchEventTemplateThinDTO) o;
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
                ", eventType=" + eventType +
                ", schLogConfiguration=" + schLogConfiguration +
                '}';
    }
}
