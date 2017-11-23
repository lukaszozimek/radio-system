package io.protone.scheduler.api.dto;

import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.scheduler.api.dto.base.SchTemplateTimeParamsDTO;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Clock.
 */
public class SchClockTemplateDTO extends SchTemplateTimeParamsDTO {

    private static final long serialVersionUID = 1L;

    private String name;

    private String shortName;

    private CorDictionaryDTO clockCategory;

    private List<SchEventTemplateDTO> schEventTemplateDTOS = new ArrayList<>();

    private List<SchEmissionTemplateDTO> emissions = new ArrayList<>();

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

    public SchClockTemplateDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchClockTemplateDTO name(String name) {
        this.name = name;
        return this;
    }

    public List<SchEmissionTemplateDTO> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<SchEmissionTemplateDTO> emissions) {
        this.emissions = emissions;
    }

    public SchClockTemplateDTO emissions(List<SchEmissionTemplateDTO> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchClockTemplateDTO addEmission(SchEmissionTemplateDTO emission) {
        this.emissions.add(emission);
        return this;
    }

    public SchClockTemplateDTO removeEmission(SchEmissionTemplateDTO emission) {
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
        SchClockTemplateDTO clock = (SchClockTemplateDTO) o;
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
        return "SchClockTemplateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", sequence=" + sequence +
                ", clockCategory=" + clockCategory +
                ", emissions=" + emissions +
                '}';
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public List<SchEventTemplateDTO> getSchEventTemplateDTOS() {
        return schEventTemplateDTOS;
    }

    public void setSchEventTemplateDTOS(List<SchEventTemplateDTO> schEventTemplateDTOS) {
        this.schEventTemplateDTOS = schEventTemplateDTOS;
    }

    public SchClockTemplateDTO sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }
}
