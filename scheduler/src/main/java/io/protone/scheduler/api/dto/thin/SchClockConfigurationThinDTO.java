package io.protone.scheduler.api.dto.thin;

import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.scheduler.api.dto.SchQueueParamsDTO;
import io.protone.scheduler.api.dto.SchTimeParamsDTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Clock.
 */
public class SchClockConfigurationThinDTO extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String name;

    private String shortName;

    private SchQueueParamsDTO queueParams;

    private CorDictionaryDTO clockCategory;

    private SchTimeParamsDTO timeParams;


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

    public SchClockConfigurationThinDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchClockConfigurationThinDTO name(String name) {
        this.name = name;
        return this;
    }


    public SchQueueParamsDTO getQueueParams() {
        return queueParams;
    }

    public void setQueueParams(SchQueueParamsDTO queueParams) {
        this.queueParams = queueParams;
    }

    public SchClockConfigurationThinDTO queueParams(SchQueueParamsDTO queueParams) {
        this.queueParams = queueParams;
        return this;
    }

    public SchTimeParamsDTO getTimeParams() {
        return timeParams;
    }

    public void setTimeParams(SchTimeParamsDTO timeParams) {
        this.timeParams = timeParams;
    }

    public SchClockConfigurationThinDTO timeParams(SchTimeParamsDTO timeParams) {
        this.timeParams = timeParams;
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
        SchClockConfigurationThinDTO clock = (SchClockConfigurationThinDTO) o;
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
        return "SchClock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", queueParams=" + queueParams +
                ", clockCategory=" + clockCategory +
                ", timeParams=" + timeParams +
                '}';
    }
}
