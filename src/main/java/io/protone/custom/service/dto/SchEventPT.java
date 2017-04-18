package io.protone.custom.service.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.domain.enumeration.SchStartTypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the SchTemplate entity.
 */
public class SchEventPT implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    private SchStartTypeEnum startType;

    private SchEventTypeEnum schEventType;

    private Long relativeDelay;

    private Long scheduledLength = 0L;

    private Long length = 0L;

    private List<SchEmissionPT> emissions = new ArrayList<>();


    public enum SchEventTypeEnum {
        MUSIC_IMPORT("ET_MUSIC_IMPORT"),

        COMMERCIAL_IMPORT("ET_COMMERCIAL_IMPORT"),

        AUTOMATION("ET_AUTOMATION"),

        AUDIO("ET_AUDIO"),

        OTHER("ET_OTHER");

        private String value;

        SchEventTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static SchEventPT.SchEventTypeEnum fromValue(String text) {
            for (SchEventPT.SchEventTypeEnum b : SchEventPT.SchEventTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public SchStartTypeEnum getStartType() {
        return startType;
    }

    public void setStartType(SchStartTypeEnum startType) {
        this.startType = startType;
    }

    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public Long getScheduledLength() {
        return scheduledLength;
    }

    public void setScheduledLength(Long scheduledLength) {
        this.scheduledLength = scheduledLength;
        this.length = scheduledLength;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {

        this.length = length;
        this.scheduledLength = length;
    }


    public List<SchEmissionPT> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<SchEmissionPT> emissions) {
        this.emissions = emissions;
    }

    public SchEventTypeEnum getSchEventType() {
        return schEventType;
    }

    public void setSchEventType(SchEventTypeEnum schEventType) {
        this.schEventType = schEventType;
    }

    public SchEventPT id(Long id) {
        this.id = id;
        return this;
    }


    public SchEventPT name(String name) {
        this.name = name;
        return this;
    }

    public SchEventPT startType(SchStartTypeEnum startType) {
        this.startType = startType;
        return this;
    }

    public SchEventPT relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }


    public SchEventPT scheduledLength(Long scheduledLength) {
        this.scheduledLength = scheduledLength;
        this.length = scheduledLength;
        return this;
    }


    public SchEventPT length(Long length) {
        this.length = length;
        this.scheduledLength = length;
        return this;
    }


    public SchEventPT emissions(List<SchEmissionPT> emissions) {
        this.emissions = emissions;
        Long totalLength = 0L;
        for (SchEmissionPT emission : emissions)
            totalLength += emission.getMediaItem().getLength();
        this.length(totalLength);
        return this;
    }

    public SchEventPT addEmission(SchEmissionPT emission) {
        this.emissions.add(emission);
        this.setLength(this.getLength() + emission.getMediaItem().getLength());
        return this;
    }

    public SchEventPT eventType(SchEventTypeEnum eventTypeEnum) {
        this.schEventType = eventTypeEnum;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchBlockPT)) return false;

        SchBlockPT that = (SchBlockPT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getStartType() != that.getStartType()) return false;
        if (getRelativeDelay() != null ? !getRelativeDelay().equals(that.getRelativeDelay()) : that.getRelativeDelay() != null)
            return false;
        if (getScheduledLength() != null ? !getScheduledLength().equals(that.getScheduledLength()) : that.getScheduledLength() != null)
            return false;
        if (getLength() != null ? !getLength().equals(that.getLength()) : that.getLength() != null) return false;
        return getEmissions() != null ? getEmissions().equals(that.getEmissions()) : that.getEmissions() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStartType() != null ? getStartType().hashCode() : 0);
        result = 31 * result + (getRelativeDelay() != null ? getRelativeDelay().hashCode() : 0);
        result = 31 * result + (getScheduledLength() != null ? getScheduledLength().hashCode() : 0);
        result = 31 * result + (getLength() != null ? getLength().hashCode() : 0);
        result = 31 * result + (getEmissions() != null ? getEmissions().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchBlockPT{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", startType=").append(startType);
        sb.append(", scheduledLength=").append(scheduledLength);
        sb.append(", emissions=").append(emissions);
        sb.append('}');
        return sb.toString();
    }
}
